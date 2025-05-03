package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.Recording;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class RecordingDto {
    private UUID id;
    private String filePath;
    private String fileName;
    private String fileType;
    private String fileSize;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isAnalyzed;
    private EmployeeShiftDto employeeShiftDto;
    private BodyCameraDto bodyCameraDto;

    public static RecordingDto fromEntity(Recording recording) {
        if(recording == null) {
            return null;
        }

        return RecordingDto.builder()
                .id(recording.getId())
                .filePath(recording.getFilePath())
                .fileName(recording.getFileName())
                .fileType(recording.getFileType())
                .fileSize(recording.getFileSize())
                .startTime(recording.getStartTime())
                .endTime(recording.getEndTime())
                .isAnalyzed(recording.getIsAnalyzed())
                .employeeShiftDto(EmployeeShiftDto.fromEntity(recording.getEmployeeShift()))
                .bodyCameraDto(BodyCameraDto.fromEntity(recording.getBodyCamera()))
                .build();
    }

    public static Recording toEntity(RecordingDto recordingDto) {
        if(recordingDto == null) {
            return null;
        }

        return Recording.builder()
                .filePath(recordingDto.getFilePath())
                .fileName(recordingDto.getFileName())
                .fileType(recordingDto.getFileType())
                .fileSize(recordingDto.getFileSize())
                .startTime(recordingDto.getStartTime())
                .endTime(recordingDto.getEndTime())
                .isAnalyzed(recordingDto.getIsAnalyzed())
                .employeeShift(EmployeeShiftDto.toEntity(recordingDto.getEmployeeShiftDto()))
                .bodyCamera(BodyCameraDto.toEntity(recordingDto.getBodyCameraDto()))
                .build();
    }
}
