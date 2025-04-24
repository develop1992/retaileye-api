package edu.ttu.retaileye.repositories;

import edu.ttu.retaileye.entities.EmployeeShiftCamera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeShiftCameraRepository extends JpaRepository<EmployeeShiftCamera, UUID> {
    @Query("""
    SELECT esc FROM EmployeeShiftCamera esc
    WHERE esc.bodyCamera.serialNumber = :serial
    AND esc.startTime <= :start
    AND (esc.endTime IS NULL OR esc.endTime >= :end)
    ORDER BY esc.startTime DESC
    """)
    Optional<EmployeeShiftCamera> findActiveAssignment(
            @Param("serial") String serial,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
    SELECT esc FROM EmployeeShiftCamera esc
    WHERE esc.employeeShift.employee.id = :employeeId
    ORDER BY esc.startTime DESC
    """)
    Optional<EmployeeShiftCamera> findTopByEmployeeIdOrderByStartTimeDesc(@Param("employeeId") UUID employeeId);
}
