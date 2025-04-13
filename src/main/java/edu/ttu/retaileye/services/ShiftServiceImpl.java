package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.Shift;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.ShiftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShiftServiceImpl implements IService<Shift, UUID> {

    private final ShiftRepository shiftRepository;

    @Override
    public Shift add(Shift shift) {
        log.info("Adding new Shift: {}", shift);
        try {
            return shiftRepository.save(shift);
        } catch (Exception e) {
            var errorMessage = "Error adding Shift";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Shift update(Shift shift) {
        log.info("Updating Shift: {}", shift);
        var existingShift = shiftRepository.findById(shift.getId())
                .orElseThrow(() -> new NotFoundException("Shift not found"));

        try {
            existingShift.setStartTime(shift.getStartTime());
            existingShift.setEndTime(shift.getEndTime());
            existingShift.setEmployees(shift.getEmployees());
            return shiftRepository.save(existingShift);
        } catch (Exception e) {
            var errorMessage = "Error updating Shift";
            log.error(errorMessage, e);
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
            var errorMessage = String.format("Error removing Shift with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Shift getById(UUID id) {
        log.info("Getting Shift with ID: {}", id);
        return shiftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shift not found with ID: " + id));
    }

    @Override
    public List<Shift> getAll() {
        log.info("Getting all Shifts");
        return shiftRepository.findAll();
    }
}
