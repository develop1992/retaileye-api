package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.entities.Recording;
import edu.ttu.retaileye.services.RecordingServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/recordings")
public class RecordingController extends BaseController<Recording, UUID> {

    public RecordingController(RecordingServiceImpl recordingService) {
        super(recordingService);
    }
}
