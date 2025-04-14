package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.IncidentDto;
import edu.ttu.retaileye.dtos.ManagerDto;
import edu.ttu.retaileye.dtos.RecordingDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncidentServiceImpl implements IService<IncidentDto, UUID> {

    private final IncidentRepository incidentRepository;

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
            log.error(errorMessage, e);
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
            log.error(errorMessage, e);
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
            var errorMessage = String.format("Error removing Incident with ID: %" + id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
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
