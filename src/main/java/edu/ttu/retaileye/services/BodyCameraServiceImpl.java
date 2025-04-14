package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.BodyCameraDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.BodyCameraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BodyCameraServiceImpl implements IService<BodyCameraDto, UUID> {

    private final BodyCameraRepository bodyCameraRepository;

    @Override
    public BodyCameraDto add(BodyCameraDto bodyCameraDto) {
        log.info("Adding new BodyCamera: {}", bodyCameraDto);

        var errorMessage = "Error adding BodyCamera";

        try {
            var bodyCamera = BodyCameraDto.toEntity(bodyCameraDto);
            return Optional.of(bodyCameraRepository.save(bodyCamera))
                    .map(BodyCameraDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public BodyCameraDto update(BodyCameraDto bodyCameraDto) {
        log.info("Updating BodyCamera: {}", bodyCameraDto);
        var existingBodyCamera = bodyCameraRepository.findById(bodyCameraDto.getId())
                .orElseThrow(() -> new NotFoundException("BodyCamera not found"));

        var errorMessage = "Error updating BodyCamera";

        try {
            existingBodyCamera.setIsActive(bodyCameraDto.getIsActive());
            existingBodyCamera.setIsAvailable(bodyCameraDto.getIsAvailable());
            existingBodyCamera.setManufacturer(bodyCameraDto.getManufacturer());
            existingBodyCamera.setModel(bodyCameraDto.getModel());
            existingBodyCamera.setSerialNumber(bodyCameraDto.getSerialNumber());
            return Optional.of(bodyCameraRepository.save(existingBodyCamera))
                    .map(BodyCameraDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
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
    public BodyCameraDto getById(UUID id) {
        log.info("Getting BodyCamera by ID: {}", id);
        return bodyCameraRepository.findById(id)
                .map(BodyCameraDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("BodyCamera not found with ID: " + id));
    }

    @Override
    public List<BodyCameraDto> getAll() {
        log.info("Getting all BodyCameras");
        return bodyCameraRepository.findAll()
                .stream()
                .map(BodyCameraDto::fromEntity)
                .toList();
    }
}
