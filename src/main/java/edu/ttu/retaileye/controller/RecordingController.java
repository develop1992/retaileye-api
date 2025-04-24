package edu.ttu.retaileye.controller;

import edu.ttu.retaileye.dtos.RecordingDto;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.services.RecordingServiceImpl;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/retaileye/recordings")
public class RecordingController extends BaseController<RecordingDto, UUID> {

    private final RecordingServiceImpl recordingService;

    public RecordingController(RecordingServiceImpl recordingService) {
        super(recordingService);
        this.recordingService = recordingService;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> streamVideo(@PathVariable UUID id) throws IOException {
        var recording = recordingService.getById(id);
        var file = new File(recording.getFilePath());

        if (!file.exists()) {
            throw new NotFoundException("File not found at: " + file.getAbsolutePath());
        }

        var resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()))
                .body(resource);
    }
}
