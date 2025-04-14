package edu.ttu.retaileye.dtos;

import edu.ttu.retaileye.entities.Manager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ManagerDto {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;

    public static ManagerDto fromEntity(Manager manager) {
        if(manager == null) {
            return null;
        }

        return ManagerDto.builder()
                .id(manager.getId())
                .firstName(manager.getFirstName())
                .middleName(manager.getMiddleName())
                .lastName(manager.getLastName())
                .address(manager.getAddress())
                .phoneNumber(manager.getPhoneNumber())
                .email(manager.getEmail())
                .build();
    }

    public static Manager toEntity(ManagerDto managerDto) {
        if (managerDto == null) {
            return null;
        }

        return Manager.builder()
                .firstName(managerDto.getFirstName())
                .middleName(managerDto.getMiddleName())
                .lastName(managerDto.getLastName())
                .address(managerDto.getAddress())
                .phoneNumber(managerDto.getPhoneNumber())
                .email(managerDto.getEmail())
                .build();
    }
}
