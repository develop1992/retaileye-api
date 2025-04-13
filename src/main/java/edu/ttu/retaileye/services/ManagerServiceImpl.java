package edu.ttu.retaileye.services;

import edu.ttu.retaileye.entities.Manager;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements IService<Manager, UUID> {

    private final ManagerRepository managerRepository;

    @Override
    public Manager add(Manager manager) {
        log.info("Adding new Manager: {}", manager);
        try {
            return managerRepository.save(manager);
        } catch (Exception e) {
            var errorMessage = "Error adding Manager";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Manager update(Manager manager) {
        log.info("Updating Manager: {}", manager);
        var existingManager = managerRepository.findById(manager.getId())
                .orElseThrow(() -> new NotFoundException("Manager not found"));

        try {
            existingManager.setFirstName(manager.getFirstName());
            existingManager.setMiddleName(manager.getMiddleName());
            existingManager.setLastName(manager.getLastName());
            existingManager.setEmail(manager.getEmail());
            existingManager.setAddress(manager.getAddress());
            existingManager.setPhoneNumber(manager.getPhoneNumber());
            existingManager.setIncidents(manager.getIncidents());
            return managerRepository.save(existingManager);
        } catch (Exception e) {
            var errorMessage = "Error updating Manager";
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public void remove(UUID id) {
        log.info("Removing Manager with ID: {}", id);

        var manager = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + id));

        try {
            managerRepository.delete(manager);
        } catch (Exception e) {
            var errorMessage = String.format("Error removing Manager with ID: %s", id);
            log.error(errorMessage, e);
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public Manager getById(UUID id) {
        log.info("Getting Manager by ID: {}", id);
        return managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + id));
    }

    @Override
    public List<Manager> getAll() {
        log.info("Getting all Managers");
        return managerRepository.findAll();
    }
}
