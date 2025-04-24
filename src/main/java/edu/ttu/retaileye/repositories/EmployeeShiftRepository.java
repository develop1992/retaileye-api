package edu.ttu.retaileye.repositories;

import edu.ttu.retaileye.entities.Employee;
import edu.ttu.retaileye.entities.EmployeeShift;
import edu.ttu.retaileye.entities.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeShiftRepository extends JpaRepository<EmployeeShift, UUID> {
    Optional<EmployeeShift> findByEmployeeId(UUID employeeId);
}
