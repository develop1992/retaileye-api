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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeShiftCameraServiceImpl implements IAssignment<EmployeeShiftCameraDto, UUID> {

    private final EmployeeShiftCameraRepository employeeShiftCameraRepository;
    private final BodyCameraServiceImpl bodyCameraService;
    private final EmployeeShiftServiceImpl employeeShiftService;

    @Override
    public EmployeeShiftCameraDto assignTo(EmployeeShiftCameraDto employeeShiftCameraDto) {
        var bodyCameraId = employeeShiftCameraDto.getBodyCameraDto().getId();
        var bodyCamera = BodyCameraDto.toEntity(bodyCameraService.getById(bodyCameraId));
        bodyCamera.setId(bodyCameraId);

        if(!bodyCamera.getIsActive()) {
            throw new InternalException("Body camera with ID: " + bodyCameraId + " is not active.");
        }

        if (!bodyCamera.getIsAvailable()) {
            throw new InternalException("Body camera with ID: " + bodyCameraId + " is not available.");
        }

        var employeeShiftId = employeeShiftCameraDto.getEmployeeShiftDto().getId();
        var employeeShift = EmployeeShiftDto.toEntity(employeeShiftService.getById(employeeShiftId));
        employeeShift.setId(employeeShiftId);

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

        var errorMessage = String.format("Error assigning body camera with ID %s to employee shift with ID %s", bodyCameraId, employeeShiftId);

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
            // Update body camera status to available
            var bodyCamera = employeeShiftCamera.getBodyCamera();
            bodyCamera.setIsAvailable(true);
            bodyCameraService.update(BodyCameraDto.fromEntity(bodyCamera));

            // Delete the EmployeeShiftCamera record
            employeeShiftCameraRepository.delete(employeeShiftCamera);
        } catch (Exception e) {
            throw new InternalException(String.format("Error unassigning body camera from employee shift camera with ID: %s", id), e);
        }
    }

    @Override
    public EmployeeShiftCameraDto getByEmployeeId(UUID id) {
        log.info("Finding employee shift camera with employee ID {}", id);
        return employeeShiftCameraRepository.findTopByEmployeeIdOrderByStartTimeDesc(id)
                .map(EmployeeShiftCameraDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Employee shift camera not found with employee ID: " + id));
    }

    public EmployeeShiftCamera getActiveAssignment(String serialNumber,
                                                  LocalDateTime startTime,
                                                  LocalDateTime endTime) {
        log.info("Finding active assignment for body camera with serial number: {}", serialNumber);

        return employeeShiftCameraRepository.findActiveAssignment(serialNumber, startTime, endTime)
                .orElseThrow(() -> new NotFoundException("No active assignment found for body camera with serial number: " + serialNumber));
    }
}
