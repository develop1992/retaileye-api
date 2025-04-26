package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.Employee;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String role;
    private String address;
    private String phoneNumber;
    private String email;

    public static EmployeeDto fromEntity(Employee employee) {
        if(employee == null) {
            return null;
        }

        return EmployeeDto.builder()
                    .id(employee.getId())
                    .firstName(employee.getFirstName())
                    .middleName(employee.getMiddleName())
                    .lastName(employee.getLastName())
                    .role(employee.getRole())
                    .address(employee.getAddress())
                    .phoneNumber(employee.getPhoneNumber())
                    .email(employee.getEmail()).build();
    }

    public static Employee toEntity(EmployeeDto employeeDto) {
        if(employeeDto == null) {
            return null;
        }

        return Employee.builder()
                    .firstName(employeeDto.getFirstName())
                    .middleName(employeeDto.getMiddleName())
                    .lastName(employeeDto.getLastName())
                    .role(employeeDto.getRole())
                    .address(employeeDto.getAddress())
                    .phoneNumber(employeeDto.getPhoneNumber())
                    .email(employeeDto.getEmail()).build();
    }

    public final String getFullName() {
        String fullName = this.firstName;

        if(!StringUtils.isBlank(this.middleName)) {
            fullName += " " + this.middleName;
        }

        if(!StringUtils.isBlank(this.lastName)) {
            fullName += " " + this.lastName;
        }

        return fullName.trim();
    }
}