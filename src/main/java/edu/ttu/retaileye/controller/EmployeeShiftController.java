package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.EmployeeShiftDto;
import edu.ttu.retaileye.services.EmployeeShiftServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retaileye/employees-shifts")
public class EmployeeShiftController {

    private final EmployeeShiftServiceImpl employeeShiftService;

    /**
     * Assign an employee to a shift.
     *
     * @param employeeShiftDto the EmployeeShiftDto object containing the assignment details
     * @return a ResponseEntity with the created EmployeeShift
     */
    @PostMapping
    public ResponseEntity<EmployeeShiftDto> assignTo(@RequestBody EmployeeShiftDto employeeShiftDto) {
        var result = employeeShiftService.assignTo(employeeShiftDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Unassign an employee from a shift.
     *
     * @param id the ID of the EmployeeShift to unassign
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        employeeShiftService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get an EmployeeShift by its employee ID.
     *
     * @param id the employee ID of the EmployeeShift to retrieve
     * @return the EmployeeShift object
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeShiftDto> getByEmployeeId(@PathVariable UUID id) {
        var employeeShiftDto = employeeShiftService.getByEmployeeId(id);
        return new ResponseEntity<>(employeeShiftDto, HttpStatus.OK);
    }
}
