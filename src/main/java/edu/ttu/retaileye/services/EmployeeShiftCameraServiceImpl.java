package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.BodyCameraDto;
import edu.ttu.retaileye.dtos.EmployeeShiftCameraDto;
import edu.ttu.retaileye.dtos.EmployeeShiftDto;
import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeShiftCameraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeShiftCameraServiceImpl implements IAssignment<EmployeeShiftCameraDto, UUID> {

    private final BodyCameraServiceImpl bodyCameraService;
    private final EmployeeShiftCameraRepository employeeShiftCameraRepository;

    @Override
    public EmployeeShiftCameraDto assignTo(EmployeeShiftCameraDto employeeShiftCameraDto) {
        var bodyCamera = BodyCameraDto.toEntity(employeeShiftCameraDto.getBodyCameraDto());
        var bodyCameraId = bodyCamera.getId();

        if(bodyCamera.getIsActive()) {
            throw new IllegalStateException("Body camera with ID: " + bodyCameraId + " is not active.");
        }

        if (bodyCamera.getIsAvailable()) {
            throw new IllegalStateException("Body camera with ID: " + bodyCameraId + " is not available.");
        }

        var employeeShift = EmployeeShiftDto.toEntity(employeeShiftCameraDto.getEmployeeShiftDto());

        // Assign camera
        var employeeShiftCamera = EmployeeShiftCamera
                .builder()
                .employeeShift(employeeShift)
                .bodyCamera(bodyCamera)
                .startTime(employeeShiftCameraDto.getStartTime())
                .endTime(employeeShiftCameraDto.getEndTime())
                .build();

        // Update body camera status
        bodyCamera.setIsAvailable(false);
        bodyCameraService.update(BodyCameraDto.fromEntity(bodyCamera));

        var errorMessage = String.format("Error assigning body camera with ID %s to employee shift with ID %s", bodyCameraId, employeeShift.getId());

        try {
            // Save the EmployeeShiftCamera record
            return Optional.of(employeeShiftCameraRepository.save(employeeShiftCamera))
                    .map(EmployeeShiftCameraDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Unassigning body camera from employee shift with ID {}", id);

        var employeeShiftCamera = employeeShiftCameraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee shift camera not found with ID: " + id));

        try {
            employeeShiftCameraRepository.delete(employeeShiftCamera);
        } catch (Exception e) {
            throw new InternalException(String.format("Error unassigning body camera from employee shift camera with ID: %s", id), e);
        }
    }

    @Override
    public EmployeeShiftCameraDto getById(UUID id) {
        log.info("Finding employee shift camera with ID {}", id);
        return employeeShiftCameraRepository.findById(id)
                .map(EmployeeShiftCameraDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Employee shift camera not found with ID: " + id));
    }
}
