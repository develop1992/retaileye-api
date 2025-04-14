package edu.ttu.retaileye.records;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
