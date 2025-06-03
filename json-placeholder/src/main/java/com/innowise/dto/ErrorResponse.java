package com.innowise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response structure")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "400")
    private int status;

    @Schema(description = "Error message", example = "Validation failed")
    private String message;

    @Schema(description = "Timestamp of the error", example = "2024-03-20T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Detailed validation errors", example = "{\"email\": \"must be a valid email address\"}")
    private Map<String, String> errors;

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
} 