package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.records.EmployeeShiftRequest;
import edu.ttu.retaileye.entities.EmployeeShift;
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
@RequestMapping("/retaileye/employeeshift")
public class EmployeeShiftController {

    private final EmployeeShiftServiceImpl employeeShiftService;

    /**
     * Assign an employee to a shift.
     *
     * @param employeeShiftRequest the request containing the employee ID and shift ID
     * @return the assigned EmployeeShift object
     */
    @PostMapping
    public ResponseEntity<EmployeeShift> assignTo(@RequestBody EmployeeShiftRequest employeeShiftRequest) {
        var result = employeeShiftService.assignTo(employeeShiftRequest.employeeId(), employeeShiftRequest.shiftId());
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
     * Get an EmployeeShift by its ID.
     *
     * @param id the ID of the EmployeeShift to retrieve
     * @return the EmployeeShift object
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeShift> getById(@PathVariable UUID id) {
        var employeeShift = employeeShiftService.getById(id);
        return new ResponseEntity<>(employeeShift, HttpStatus.OK);
    }
}
