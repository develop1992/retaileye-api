package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.entities.Shift;
import edu.ttu.retaileye.services.ShiftServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/shifts")
public class ShiftController extends BaseController<Shift, UUID> {

    public ShiftController(ShiftServiceImpl shiftService) {
        super(shiftService);
    }
}
