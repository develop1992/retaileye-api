package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.ShiftDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.ShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftServiceImpl implements IService<ShiftDto, UUID> {

    private final ShiftRepository shiftRepository;

    @Override
    public ShiftDto add(ShiftDto shiftDto) {
        log.info("Adding new Shift: {}", shiftDto);

        var errorMessage = "Error adding Shift";

        try {
            var shift = ShiftDto.toEntity(shiftDto);
            return Optional.of(shiftRepository.save(shift))
                    .map(ShiftDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public ShiftDto update(ShiftDto shiftDto) {
        log.info("Updating Shift: {}", shiftDto);
        var existingShift = shiftRepository.findById(shiftDto.getId())
                .orElseThrow(() -> new NotFoundException("Shift not found"));

        var errorMessage = "Error updating Shift";

        try {
            existingShift.setStartTime(shiftDto.getStartTime());
            existingShift.setEndTime(shiftDto.getEndTime());
            existingShift.setType(shiftDto.getType());
            existingShift.setIsAvailable(shiftDto.getIsAvailable());
            return Optional.of(shiftRepository.save(existingShift))
                    .map(ShiftDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Shift with ID: {}", id);

        var shift = shiftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shift not found with ID: " + id));

        try {
            shiftRepository.delete(shift);
        } catch (Exception e) {
            throw new InternalException(String.format("Error removing Shift with ID: %s", id), e);
        }
    }

    @Override
    public ShiftDto getById(UUID id) {
        log.info("Getting Shift with ID: {}", id);
        return shiftRepository.findById(id)
                .map(ShiftDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Shift not found with ID: " + id));
    }

    @Override
    public List<ShiftDto> getAll() {
        log.info("Getting all Shifts");
        return shiftRepository.findAll()
                .stream()
                .map(ShiftDto::fromEntity)
                .toList();
    }
}
