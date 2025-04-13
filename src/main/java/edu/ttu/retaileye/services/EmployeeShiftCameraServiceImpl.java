package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeShiftCameraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeShiftCameraServiceImpl implements IAssignment<EmployeeShiftCamera, UUID> {

    private final BodyCameraServiceImpl bodyCameraService;
    private final EmployeeShiftServiceImpl employeeShiftService;
    private final EmployeeShiftCameraRepository employeeShiftCameraRepository;

    @Override
    public EmployeeShiftCamera assignTo(UUID employeeShiftId, UUID bodyCameraId) {
        var bodyCamera = bodyCameraService.getById(bodyCameraId);

        if(bodyCamera.getIsActive()) {
            throw new IllegalStateException("Body camera with ID: " + bodyCameraId + " is not active.");
        }

        if (bodyCamera.getIsAvailable()) {
            throw new IllegalStateException("Body camera with ID: " + bodyCameraId + " is not available.");
        }

        // Find or create the EmployeeShift record
        var employeeShift = employeeShiftService.getById(employeeShiftId);

        // Assign camera
        var employeeShiftCamera = EmployeeShiftCamera
                .builder()
                .employeeShift(employeeShift)
                .bodyCamera(bodyCamera)
                .build();

        // Update body camera status
        bodyCamera.setIsAvailable(false);
        bodyCameraService.update(bodyCamera);

        try {
            // Save the EmployeeShiftCamera record
            return employeeShiftCameraRepository.save(employeeShiftCamera);
        } catch (Exception e) {
            var errorMessage = String.format("Error assigning body camera with ID %s to employee shift with ID %s", bodyCameraId, employeeShiftId);
            log.error(errorMessage, e);
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
            var errorMessage = String.format("Error unassigning body camera from employee shift camera with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public EmployeeShiftCamera getById(UUID id) {
        log.info("Finding employee shift camera with ID {}", id);
        return employeeShiftCameraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee shift camera not found with ID: " + id));
    }
}
