package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.Incident;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncidentServiceImpl implements IService<Incident, UUID> {

    private final IncidentRepository incidentRepository;

    @Override
    public Incident add(Incident incident) {
        log.info("Adding new Incident: {}", incident);
        try {
            return incidentRepository.save(incident);
        } catch (Exception e) {
            var errorMessage = "Error adding Incident";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Incident update(Incident incident) {
        log.info("Updating Incident: {}", incident);
        var existingIncident = incidentRepository.findById(incident.getId())
                .orElseThrow(() -> new NotFoundException("Incident not found"));

        try {
            existingIncident.setDescription(incident.getDescription());
            existingIncident.setStatus(incident.getStatus());
            existingIncident.setManager(incident.getManager());
            existingIncident.setRecording(incident.getRecording());
            existingIncident.setSeverity(incident.getSeverity());
            existingIncident.setOccurrenceTime(incident.getOccurrenceTime());
            return incidentRepository.save(existingIncident);
        } catch (Exception e) {
            var errorMessage = "Error updating Incident";
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
    public Incident getById(UUID id) {
        log.info("Getting Incident by ID: {}", id);
        return incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Incident not found with ID: " + id));
    }

    @Override
    public List<Incident> getAll() {
        log.info("Getting all Incidents");
        return incidentRepository.findAll();
    }
}
