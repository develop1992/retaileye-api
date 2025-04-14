package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.Incident;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class IncidentDto {
    private UUID id;
    private LocalDateTime occurrenceTime;
    private String severity;
    private String description;
    private String status;
    private RecordingDto recordingDto;
    private ManagerDto managerDto;

    public static IncidentDto fromEntity(Incident incident) {
        if (incident == null) {
            return null;
        }

        return IncidentDto.builder()
                .id(incident.getId())
                .occurrenceTime(incident.getOccurrenceTime())
                .severity(incident.getSeverity())
                .description(incident.getDescription())
                .status(incident.getStatus())
                .recordingDto(RecordingDto.fromEntity(incident.getRecording()))
                .managerDto(ManagerDto.fromEntity(incident.getManager()))
                .build();
    }

    public static Incident toEntity(IncidentDto incidentDto) {
        if (incidentDto == null) {
            return null;
        }

        return Incident.builder()
                .occurrenceTime(incidentDto.getOccurrenceTime())
                .severity(incidentDto.getSeverity())
                .description(incidentDto.getDescription())
                .status(incidentDto.getStatus())
                .recording(RecordingDto.toEntity(incidentDto.getRecordingDto()))
                .manager(ManagerDto.toEntity(incidentDto.getManagerDto()))
                .build();
    }
}
