package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.IncidentDto;
import edu.ttu.retaileye.services.IncidentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/retaileye/incidents")
public class IncidentController extends BaseController<IncidentDto, UUID> {

    private final IncidentServiceImpl incidentService;

    public IncidentController(IncidentServiceImpl incidentService) {
        super(incidentService);
        this.incidentService = incidentService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<IncidentDto>> addAll(@RequestBody List<IncidentDto> incidentDtoList) {
        var saved = incidentService.addAll(incidentDtoList);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> removeAll() {
        incidentService.removeAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
