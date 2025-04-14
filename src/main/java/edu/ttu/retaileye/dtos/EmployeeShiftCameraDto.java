package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.BodyCamera;
import edu.ttu.retaileye.entities.EmployeeShift;
import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class EmployeeShiftCameraDto {
    private UUID id;
    private BodyCameraDto bodyCameraDto;
    private EmployeeShiftDto employeeShiftDto;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static EmployeeShiftCameraDto fromEntity(EmployeeShiftCamera employeeShiftCamera) {
        if(employeeShiftCamera == null) {
            return null;
        }

        return EmployeeShiftCameraDto.builder()
                        .id(employeeShiftCamera.getId())
                        .bodyCameraDto(BodyCameraDto.fromEntity(employeeShiftCamera.getBodyCamera()))
                        .employeeShiftDto(EmployeeShiftDto.fromEntity(employeeShiftCamera.getEmployeeShift()))
                        .startTime(employeeShiftCamera.getStartTime())
                        .endTime(employeeShiftCamera.getEndTime())
                        .build();
    }

    public static EmployeeShiftCamera toEntity(EmployeeShiftCameraDto employeeShiftCameraDto) {
        if(employeeShiftCameraDto == null) {
            return null;
        }

        return EmployeeShiftCamera.builder()
                        .bodyCamera(BodyCameraDto.toEntity(employeeShiftCameraDto.getBodyCameraDto()))
                        .employeeShift(EmployeeShiftDto.toEntity(employeeShiftCameraDto.getEmployeeShiftDto()))
                        .startTime(employeeShiftCameraDto.getStartTime())
                        .endTime(employeeShiftCameraDto.getEndTime())
                        .build();
    }
}