package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.records.EmployeeShiftCameraRequest;
import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import edu.ttu.retaileye.services.EmployeeShiftCameraServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retaileye/employeeshiftcamera")
public class EmployeeShiftCameraController {

    private final EmployeeShiftCameraServiceImpl employeeShiftCameraService;

    /**
     * Assign a body camera to an employee shift.
     *
     * @param assignmentRequest the assignment request containing the employee shift ID and body camera ID
     * @return the assigned EmployeeShiftCamera object
     */
    @PostMapping
    public ResponseEntity<EmployeeShiftCamera> assignTo(@RequestBody EmployeeShiftCameraRequest assignmentRequest) {
        var result = employeeShiftCameraService.assignTo(assignmentRequest.employeeShiftId(), assignmentRequest.bodyCameraId());
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
    public ResponseEntity<EmployeeShiftCamera> getById(@PathVariable UUID id) {
        var employeeShiftCamera = employeeShiftCameraService.getById(id);
        return new ResponseEntity<>(employeeShiftCamera, HttpStatus.OK);
    }
}
