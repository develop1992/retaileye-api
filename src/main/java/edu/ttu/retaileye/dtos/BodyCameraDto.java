package edu.ttu.retaileye.dtos;


import edu.ttu.retaileye.entities.BodyCamera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class BodyCameraDto {
    private UUID id;
    private String serialNumber;
    private String model;
    private String manufacturer;
    private Boolean isAvailable;
    private Boolean isActive;

    public static BodyCameraDto fromEntity(BodyCamera bodyCamera) {
        if(bodyCamera == null) {
            return null;
        }

        return BodyCameraDto.builder()
                .id(bodyCamera.getId())
                .serialNumber(bodyCamera.getSerialNumber())
                .model(bodyCamera.getModel())
                .manufacturer(bodyCamera.getManufacturer())
                .isAvailable(bodyCamera.getIsAvailable())
                .isActive(bodyCamera.getIsActive()).build();
    }

    public static BodyCamera toEntity(BodyCameraDto bodyCameraDto) {
        if(bodyCameraDto == null) {
            return null;
        }

        return BodyCamera.builder()
                .serialNumber(bodyCameraDto.getSerialNumber())
                .model(bodyCameraDto.getModel())
                .manufacturer(bodyCameraDto.getManufacturer())
                .isAvailable(bodyCameraDto.getIsAvailable())
                .isActive(bodyCameraDto.getIsActive()).build();
    }
}
