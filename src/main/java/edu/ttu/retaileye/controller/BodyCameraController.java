package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.BodyCameraDto;
import edu.ttu.retaileye.services.BodyCameraServiceImpl;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/retaileye/body-cameras")
public class BodyCameraController extends BaseController<BodyCameraDto, UUID> {

    public BodyCameraController(BodyCameraServiceImpl bodyCameraService) {
        super(bodyCameraService);
    }
}
