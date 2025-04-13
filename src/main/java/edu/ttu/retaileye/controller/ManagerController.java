package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.entities.Manager;
import edu.ttu.retaileye.services.ManagerServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/managers")
public class ManagerController extends BaseController<Manager, UUID> {

    public ManagerController(ManagerServiceImpl managerService) {
        super(managerService);
    }
}
