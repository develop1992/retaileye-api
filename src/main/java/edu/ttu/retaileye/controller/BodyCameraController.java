package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.entities.BodyCamera;
import edu.ttu.retaileye.services.BodyCameraServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/bodycameras")
public class BodyCameraController extends BaseController<BodyCamera, UUID> {

    public BodyCameraController(BodyCameraServiceImpl bodyCameraService) {
        super(bodyCameraService);
    }
}
