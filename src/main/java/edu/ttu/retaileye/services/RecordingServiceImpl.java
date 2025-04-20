package edu.ttu.retaileye.services;

import edu.ttu.retaileye.ai.VideoAnalysisService;
import edu.ttu.retaileye.dtos.BodyCameraDto;
import edu.ttu.retaileye.dtos.EmployeeShiftDto;
import edu.ttu.retaileye.dtos.RecordingDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.RecordingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordingServiceImpl implements IService<RecordingDto, UUID> {

    private final RecordingRepository recordingRepository;
    private final VideoAnalysisService videoAnalysisService;

    @Override
    public RecordingDto add(RecordingDto recordingDto) {
        log.info("Adding new Recording: {}", recordingDto);

        var errorMessage = "Error adding Recording";

        try {
            var recording = RecordingDto.toEntity(recordingDto);
            return Optional.of(recordingRepository.save(recording))
                    .map(RecordingDto::fromEntity)
                    .map(recDto -> {
                        videoAnalysisService.analyzeVideo(recDto.getFileName());
                        return recDto;
                    })
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public RecordingDto update(RecordingDto recordingDto) {
        log.info("Updating Recording: {}", recordingDto);
        var existingRecording = recordingRepository.findById(recordingDto.getId())
                .orElseThrow(() -> new NotFoundException("Recording not found"));

        var errorMessage = "Error updating Recording";

        try {
            existingRecording.setFilePath(recordingDto.getFilePath());
            existingRecording.setFileName(recordingDto.getFileName());
            existingRecording.setFileType(recordingDto.getFileType());
            existingRecording.setFileSize(recordingDto.getFileSize());
            existingRecording.setBodyCamera(BodyCameraDto.toEntity(recordingDto.getBodyCameraDto()));
            existingRecording.setEmployeeShift(EmployeeShiftDto.toEntity(recordingDto.getEmployeeShiftDto()));
            existingRecording.setStartTime(recordingDto.getStartTime());
            existingRecording.setEndTime(recordingDto.getEndTime());
            return Optional.of(recordingRepository.save(existingRecording))
                    .map(RecordingDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Recording with ID: {}", id);

        var recording = recordingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recording not found with ID: " + id));

        try {
//            // Delete the actual file from disk
//            var path = recording.getFilePath();
//            var file = new File(path);
//            if (file.exists() && file.isFile()) {
//                boolean deleted = file.delete();
//                if (!deleted) {
//                    log.warn("Failed to delete video file: {}", path);
//                }
//            }

            recordingRepository.delete(recording);
        } catch (Exception e) {
            throw new InternalException(String.format("Error removing Recording with ID: %s", id), e);
        }
    }

    @Override
    public RecordingDto getById(UUID id) {
        log.info("Getting Recording by ID: {}", id);
        return recordingRepository.findById(id)
                .map(RecordingDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Recording not found with ID: " + id));
    }

    @Override
    public List<RecordingDto> getAll() {
        log.info("Getting all Recordings");
        return recordingRepository.findAll()
                .stream()
                .map(RecordingDto::fromEntity)
                .toList();
    }
}
