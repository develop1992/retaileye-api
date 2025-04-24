package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.EmployeeDto;
import edu.ttu.retaileye.dtos.EmployeeShiftDto;
import edu.ttu.retaileye.dtos.ShiftDto;
import edu.ttu.retaileye.entities.EmployeeShift;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeShiftServiceImpl implements IAssignment<EmployeeShiftDto, UUID> {

    private final EmployeeShiftRepository employeeShiftRepository;
    private final EmployeeServiceImpl employeeService;
    private final ShiftServiceImpl shiftService;

    @Override
    public EmployeeShiftDto assignTo(EmployeeShiftDto employeeShiftDto) {
        var employeeId = employeeShiftDto.getEmployeeDto().getId();
        var employee = EmployeeDto.toEntity(employeeService.getById(employeeId));
        employee.setId(employeeId);

        var shiftId = employeeShiftDto.getShiftDto().getId();
        var shift = ShiftDto.toEntity(shiftService.getById(shiftId));
        shift.setId(shiftId);

        log.info("Assigning employee with ID {} to shift with ID {}", employeeId, shiftId);

        var employeeShift = EmployeeShift.builder()
                .employee(employee)
                .shift(shift)
                .build();

        var errorMessage = String.format("Error assigning employee with ID %s to shift with ID %s", employeeId, shiftId);

        try {
            return Optional.of(employeeShiftRepository.save(employeeShift))
                    .map(EmployeeShiftDto::fromEntity)
                    .orElseThrow(() -> new NotFoundException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
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
            throw new InternalException(String.format("Error unassigning employee shift with ID: %s", id), e);
        }
    }

    @Override
    public EmployeeShiftDto getByEmployeeId(UUID id) {
        log.info("Finding employee shift with employee ID {}", id);
        return employeeShiftRepository.findByEmployeeId(id)
                .map(EmployeeShiftDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Employee shift not found with employee ID: " + id));
    }

    public EmployeeShiftDto getById(UUID id) {
        log.info("Finding employee shift with ID {}", id);
        return employeeShiftRepository.findById(id)
                .map(EmployeeShiftDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Employee shift not found with ID: " + id));
    }
}
