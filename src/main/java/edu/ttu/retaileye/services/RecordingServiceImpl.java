package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.Recording;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.RecordingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordingServiceImpl implements IService<Recording, UUID> {

    private final RecordingRepository recordingRepository;

    @Override
    public Recording add(Recording recording) {
        log.info("Adding new Recording: {}", recording);
        try {
            return recordingRepository.save(recording);
        } catch (Exception e) {
            var errorMessage = "Error adding Recording";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Recording update(Recording recording) {
        log.info("Updating Recording: {}", recording);
        var existingRecording = recordingRepository.findById(recording.getId())
                .orElseThrow(() -> new NotFoundException("Recording not found"));

        try {
            existingRecording.setFilePath(recording.getFilePath());
            existingRecording.setBodyCamera(recording.getBodyCamera());
            existingRecording.setEmployeeShift(recording.getEmployeeShift());
            existingRecording.setStartTime(recording.getStartTime());
            existingRecording.setIncidents(recording.getIncidents());
            existingRecording.setEndTime(recording.getEndTime());
            return recordingRepository.save(existingRecording);
        } catch (Exception e) {
            var errorMessage = "Error updating Recording";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Recording with ID: {}", id);

        var recording = recordingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recording not found with ID: " + id));

        try {
            recordingRepository.delete(recording);
        } catch (Exception e) {
            var errorMessage = String.format("Error removing Recording with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Recording getById(UUID id) {
        log.info("Getting Recording by ID: {}", id);
        return recordingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recording not found with ID: " + id));
    }

    @Override
    public List<Recording> getAll() {
        log.info("Getting all Recordings");
        return recordingRepository.findAll();
    }
}
