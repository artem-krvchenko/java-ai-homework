package com.innowise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Company data transfer object")
public class CompanyDto {
    @NotBlank(message = "Company name is required")
    @Schema(description = "Company name", example = "Romaguera-Crona")
    private String name;

    @NotBlank(message = "Catch phrase is required")
    @Schema(description = "Company catch phrase", example = "Multi-layered client-server neural-net")
    private String catchPhrase;

    @NotBlank(message = "Business description is required")
    @Schema(description = "Business description", example = "harness real-time e-markets")
    private String bs;
} 