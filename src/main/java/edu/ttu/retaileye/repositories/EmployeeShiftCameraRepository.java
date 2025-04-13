package edu.ttu.retaileye.repositories;

import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeShiftCameraRepository extends JpaRepository<EmployeeShiftCamera, UUID> {
    // Custom query methods can be defined here if needed
}
