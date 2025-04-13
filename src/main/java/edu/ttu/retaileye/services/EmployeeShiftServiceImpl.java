package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.EmployeeShift;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeShiftServiceImpl implements IAssignment<EmployeeShift, UUID> {

    private final EmployeeServiceImpl employeeService;
    private final ShiftServiceImpl shiftService;
    private final EmployeeShiftRepository employeeShiftRepository;

    @Override
    public EmployeeShift assignTo(UUID employeeId, UUID shiftId) {
        log.info("Assigning employee with ID {} to shift with ID {}", employeeId, shiftId);

        var employee = employeeService.getById(employeeId);
        var shift = shiftService.getById(shiftId);

        var employeeShift = EmployeeShift.builder()
                .employee(employee)
                .shift(shift)
                .build();

        try {
            return employeeShiftRepository.save(employeeShift);
        } catch (Exception e) {
            var errorMessage = String.format("Error assigning employee with ID %s to shift with ID %s", employeeId, shiftId);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Unassigning employee shift with ID {}", id);

        var employeeShift = employeeShiftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee shift not found with ID: " + id));

        try {
            employeeShiftRepository.delete(employeeShift);
        } catch (Exception e) {
            var errorMessage = String.format("Error unassigning employee shift with ID: %s", id);
            log.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public EmployeeShift getById(UUID id) {
        log.info("Finding employee shift with ID {}", id);
        return employeeShiftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee shift not found with ID: " + id));
    }
}
