package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.EmployeeDto;
import edu.ttu.retaileye.services.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/employees")
public class EmployeeController extends BaseController<EmployeeDto, UUID> {

    public EmployeeController(EmployeeServiceImpl employeeService) {
        super(employeeService);
    }
}