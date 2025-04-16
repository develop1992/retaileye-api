package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.Shift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ShiftDto {
    private UUID id;
    private Boolean isAvailable;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static ShiftDto fromEntity(Shift shift) {
        if(shift == null) {
            return null;
        }

        return ShiftDto.builder()
                .id(shift.getId())
                .isAvailable(shift.getIsAvailable())
                .type(shift.getType())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .build();
    }

    public static Shift toEntity(ShiftDto shiftDto) {
        if(shiftDto == null) {
            return null;
        }

        return Shift.builder()
                .isAvailable(shiftDto.getIsAvailable())
                .type(shiftDto.getType())
                .startTime(shiftDto.getStartTime())
                .endTime(shiftDto.getEndTime())
                .build();
    }
}
