package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.EmployeeShift;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class EmployeeShiftDto {
    private UUID id;
    private EmployeeDto employeeDto;
    private ShiftDto shiftDto;

    public static EmployeeShiftDto fromEntity(EmployeeShift employeeShift) {
        if(employeeShift == null) {
            return null;
        }

        return EmployeeShiftDto.builder()
                .id(employeeShift.getId())
                .employeeDto(EmployeeDto.fromEntity(employeeShift.getEmployee()))
                .shiftDto(ShiftDto.fromEntity(employeeShift.getShift()))
                .build();
    }

    public static EmployeeShift toEntity(EmployeeShiftDto employeeShiftDto) {
        if(employeeShiftDto == null) {
            return null;
        }

        return EmployeeShift.builder()
                .employee(EmployeeDto.toEntity(employeeShiftDto.getEmployeeDto()))
                .shift(ShiftDto.toEntity(employeeShiftDto.getShiftDto()))
                .build();
    }
}
