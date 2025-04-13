package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.entities.Incident;
import edu.ttu.retaileye.services.IncidentServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/incidents")
public class IncidentController extends BaseController<Incident, UUID> {

    public IncidentController(IncidentServiceImpl incidentService) {
        super(incidentService);
    }
}
