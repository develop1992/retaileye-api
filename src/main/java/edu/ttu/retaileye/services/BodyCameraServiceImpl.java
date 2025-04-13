package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.BodyCamera;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.BodyCameraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BodyCameraServiceImpl implements IService<BodyCamera, UUID> {

    private final BodyCameraRepository bodyCameraRepository;

    @Override
    public BodyCamera add(BodyCamera bodyCamera) {
        log.info("Adding new BodyCamera: {}", bodyCamera);
        try {
            return bodyCameraRepository.save(bodyCamera);
        } catch (Exception e) {
            var errorMessage = "Error adding BodyCamera";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public BodyCamera update(BodyCamera bodyCamera) {
        log.info("Updating BodyCamera: {}", bodyCamera);
        var existingBodyCamera = bodyCameraRepository.findById(bodyCamera.getId())
                .orElseThrow(() -> new NotFoundException("BodyCamera not found"));

        try {
            existingBodyCamera.setIsActive(bodyCamera.getIsActive());
            existingBodyCamera.setIsAvailable(bodyCamera.getIsAvailable());
            existingBodyCamera.setRecordings(bodyCamera.getRecordings());
            return bodyCameraRepository.save(existingBodyCamera);
        } catch (Exception e) {
            var errorMessage = "Error updating BodyCamera";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing BodyCamera with ID: {}", id);

        var bodyCamera = bodyCameraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BodyCamera not found"));

        try {
            bodyCameraRepository.delete(bodyCamera);
        } catch (Exception e) {
            var errorMessage = String.format("Error removing BodyCamera with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public BodyCamera getById(UUID id) {
        log.info("Getting BodyCamera by ID: {}", id);
        return bodyCameraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BodyCamera not found with ID: " + id));
    }

    @Override
    public List<BodyCamera> getAll() {
        log.info("Getting all BodyCameras");
        return bodyCameraRepository.findAll();
    }
}
