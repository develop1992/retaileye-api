package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.EmployeeShiftCameraDto;
import edu.ttu.retaileye.services.EmployeeShiftCameraServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retaileye/employees-shifts-cameras")
public class EmployeeShiftCameraController {

    private final EmployeeShiftCameraServiceImpl employeeShiftCameraService;

    /**
     * Assign a body camera to an employee shift.
     *
     * @param employeeShiftCameraDto the EmployeeShiftCameraDto object containing the assignment details
     * @return a ResponseEntity with the created EmployeeShiftCameraDto
     */
    @PostMapping
    public ResponseEntity<EmployeeShiftCameraDto> assignTo(@RequestBody EmployeeShiftCameraDto employeeShiftCameraDto) {
        var result = employeeShiftCameraService.assignTo(employeeShiftCameraDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * Unassign a body camera from an employee shift.
     *
     * @param id the ID of the EmployeeShiftCamera to unassign
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        employeeShiftCameraService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get an EmployeeShiftCamera by its ID.
     *
     * @param id the ID of the EmployeeShiftCamera to retrieve
     * @return the EmployeeShiftCamera object
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeShiftCameraDto> getById(@PathVariable UUID id) {
        var employeeShiftCameraDto = employeeShiftCameraService.getById(id);
        return new ResponseEntity<>(employeeShiftCameraDto, HttpStatus.OK);
    }
}
