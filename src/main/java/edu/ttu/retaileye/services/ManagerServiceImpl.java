package edu.ttu.retaileye.services;

import edu.ttu.retaileye.dtos.ManagerDto;
import edu.ttu.retaileye.exceptions.InternalException;
import edu.ttu.retaileye.exceptions.NotFoundException;
import edu.ttu.retaileye.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerServiceImpl implements IService<ManagerDto, UUID> {

    private final ManagerRepository managerRepository;

    @Override
    public ManagerDto add(ManagerDto managerDto) {
        log.info("Adding new Manager: {}", managerDto);

        var errorMessage = "Error adding Manager";

        try {
            var manager = ManagerDto.toEntity(managerDto);
            return Optional.of(managerRepository.save(manager))
                    .map(ManagerDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
            throw new InternalException(errorMessage, e);
        }
    }

    @Override
    public ManagerDto update(ManagerDto managerDto) {
        log.info("Updating Manager: {}", managerDto);
        var existingManager = managerRepository.findById(managerDto.getId())
                .orElseThrow(() -> new NotFoundException("Manager not found"));

        var errorMessage = "Error updating Manager";

        try {
            existingManager.setFirstName(managerDto.getFirstName());
            existingManager.setMiddleName(managerDto.getMiddleName());
            existingManager.setLastName(managerDto.getLastName());
            existingManager.setEmail(managerDto.getEmail());
            existingManager.setAddress(managerDto.getAddress());
            existingManager.setPhoneNumber(managerDto.getPhoneNumber());
            return Optional.of(managerRepository.save(existingManager))
                    .map(ManagerDto::fromEntity)
                    .orElseThrow(() -> new InternalException(errorMessage));
        } catch (Exception e) {
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
            throw new InternalException(String.format("Error removing Manager with ID: %s", id), e);
        }
    }

    @Override
    public ManagerDto getById(UUID id) {
        log.info("Getting Manager by ID: {}", id);
        return managerRepository.findById(id)
                .map(ManagerDto::fromEntity)
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + id));
    }

    @Override
    public List<ManagerDto> getAll() {
        log.info("Getting all Managers");
        return managerRepository.findAll()
                .stream()
                .map(ManagerDto::fromEntity)
                .toList();
    }
}
