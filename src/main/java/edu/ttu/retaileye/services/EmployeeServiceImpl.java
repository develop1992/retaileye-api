package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.Employee;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements IService<Employee, UUID> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee add(Employee employee) {
        log.info("Adding new Employee: {}", employee);
        try {
            return employeeRepository.save(employee);
        } catch (Exception e) {
            var errorMessage = "Error adding Employee";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Employee update(Employee employee) {
        log.info("Updating Employee: {}", employee);
        var existingEmployee = employeeRepository.findById(employee.getId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        try {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setMiddleName(employee.getMiddleName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setShifts(employee.getShifts());
            return employeeRepository.save(existingEmployee);
        } catch (Exception e) {
            var errorMessage = "Error updating Employee";
            log.error(errorMessage, e);
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
            var errorMessage = String.format("Error removing Employee with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Employee getById(UUID id) {
        log.info("Getting Employee by ID: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with ID: " + id));
    }

    @Override
    public List<Employee> getAll() {
        log.info("Getting all Employees");
        return employeeRepository.findAll();
    }
}
