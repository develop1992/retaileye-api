package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.ShiftDto;
import edu.ttu.retaileye.services.ShiftServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/shifts")
public class ShiftController extends BaseController<ShiftDto, UUID> {

    public ShiftController(ShiftServiceImpl shiftService) {
        super(shiftService);
    }
}
