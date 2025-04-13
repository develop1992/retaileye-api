package edu.ttu.retaileye.repositories;

import edu.ttu.retaileye.entities.BodyCamera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BodyCameraRepository extends JpaRepository<BodyCamera, UUID> {
    // Custom query methods can be defined here if needed
}
