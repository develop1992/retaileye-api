package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.EmployeeDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements IService<EmployeeDto, UUID> {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto add(EmployeeDto employeeDto) {
        log.info("Adding new Employee: {}", employeeDto);

        var errorMessage = "Error adding Employee";

        try {
            var employee = EmployeeDto.toEntity(employeeDto);
            return Optional.of(employeeRepository.save(employee))
                    .map(EmployeeDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        log.info("Updating Employee: {}", employeeDto);
        var existingEmployee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        var errorMessage = "Error updating Employee";

        try {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setMiddleName(employeeDto.getMiddleName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());
            existingEmployee.setRole(employeeDto.getRole());
            existingEmployee.setAddress(employeeDto.getAddress());
            existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
            return Optional.of(employeeRepository.save(existingEmployee))
                    .map(EmployeeDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Employee with ID: {}", id);

        var employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));

        try {
            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new InternalException(String.format("Error removing Employee with ID: %s", id), e);
        }
    }

    @Override
    public EmployeeDto getById(UUID id) {
        log.info("Getting Employee by ID: {}", id);
        return employeeRepository.findById(id)
                .map(EmployeeDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<EmployeeDto> getAll() {
        log.info("Getting all Employees");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDto::fromEntity)
                .toList();
    }

    public Optional<EmployeeDto> getByRole(String role) {
        log.info("Getting Employee by role: {}", role);
        return employeeRepository.findByRoleIgnoreCase(role)
                .map(EmployeeDto::fromEntity);
    }
}
