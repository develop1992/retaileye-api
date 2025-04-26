package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.IncidentDto;
import edu.ttu.retaileye.dtos.ManagerDto;
import edu.ttu.retaileye.dtos.RecordingDto;
import edu.ttu.retaileye.entities.Incident;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncidentServiceImpl implements IService<IncidentDto, UUID> {

    private final IncidentRepository incidentRepository;
    private final RecordingServiceImpl recordingService;
    private final EmailService emailService;

    /**
     * Adds a list of incidents to the database.
     *
     * @param incidentDtoList the list of incidents to add
     * @return the list of added incidents
     */
    public List<IncidentDto> addAll(List<IncidentDto> incidentDtoList) {
        log.info("Adding {} Incidents", incidentDtoList.size());

        var incidents = new ArrayList<Incident>();

        for (IncidentDto incidentDto : incidentDtoList) {
            var incident = IncidentDto.toEntity(incidentDto);

            // Fetch the managed Recording entity and attach it
            if (incidentDto.getRecordingDto() != null && incidentDto.getRecordingDto().getId() != null) {
                var recording = RecordingDto.toEntity(recordingService.getById(incidentDto.getRecordingDto().getId()));
                recording.setId(incidentDto.getRecordingDto().getId());
                incident.setRecording(recording);
            }

            incidents.add(incident);
        }

        // Save incidents
        var savedIncidents = incidentRepository.saveAll(incidents)
                .stream()
                .map(IncidentDto::fromEntity)
                .toList();

        // Send email notification to manager after incidents are saved
        emailService.sendEmail(savedIncidents);

        return savedIncidents;
    }

    @Override
    public IncidentDto add(IncidentDto incidentDto) {
        log.info("Adding new Incident: {}", incidentDto);

        var errorMessage = "Error adding Incident";

        try {
            var incident = IncidentDto.toEntity(incidentDto);
            return Optional.of(incidentRepository.save(incident))
                    .map(IncidentDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public IncidentDto update(IncidentDto incidentDto) {
        log.info("Updating Incident: {}", incidentDto);
        var existingIncident = incidentRepository.findById(incidentDto.getId())
                .orElseThrow(() -> new NotFoundException("Incident not found"));

        var errorMessage = "Error updating Incident";

        try {
            existingIncident.setDescription(incidentDto.getDescription());
            existingIncident.setStatus(incidentDto.getStatus());
            existingIncident.setManager(ManagerDto.toEntity(incidentDto.getManagerDto()));
            existingIncident.setRecording(RecordingDto.toEntity(incidentDto.getRecordingDto()));
            existingIncident.setSeverity(incidentDto.getSeverity());
            existingIncident.setOccurrenceTime(incidentDto.getOccurrenceTime());
            return Optional.of(incidentRepository.save(existingIncident))
                    .map(IncidentDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Incident with ID: {}", id);

        var incident = incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incident not found with ID: " + id));

        try {
            incidentRepository.delete(incident);
        } catch (Exception e) {
            throw new InternalException(String.format("Error removing Incident with ID: %" + id), e);
        }
    }

    public void removeAll() {
        log.info("Removing all Incidents");
        incidentRepository.deleteAll();
    }

    @Override
    public IncidentDto getById(UUID id) {
        log.info("Getting Incident by ID: {}", id);
        return incidentRepository.findById(id)
                .map(IncidentDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Incident not found with ID: " + id));
    }

    @Override
    public List<IncidentDto> getAll() {
        log.info("Getting all Incidents");
        return incidentRepository.findAll()
                .stream()
                .map(IncidentDto::fromEntity)
                .toList();
    }
}
