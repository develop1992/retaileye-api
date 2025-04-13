package edu.ttu.retaileye.records;

import java.util.UUID;

public record EmployeeShiftRequest(UUID employeeId, UUID shiftId) {}

