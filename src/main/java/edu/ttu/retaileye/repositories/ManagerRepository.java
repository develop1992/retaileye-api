package edu.ttu.retaileye.repositories;

import edu.ttu.retaileye.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    // Custom query methods can be defined here if needed
}
