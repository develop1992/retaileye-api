package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.IncidentDto;
import edu.ttu.retaileye.services.IncidentServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/incidents")
public class IncidentController extends BaseController<IncidentDto, UUID> {

    public IncidentController(IncidentServiceImpl incidentService) {
        super(incidentService);
    }
}
