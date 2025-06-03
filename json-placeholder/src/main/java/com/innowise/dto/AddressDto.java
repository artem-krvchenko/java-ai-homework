package com.innowise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Address data transfer object")
public class AddressDto {
    @NotBlank(message = "Street is required")
    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @NotBlank(message = "Suite is required")
    @Schema(description = "Suite or apartment number", example = "Apt 4B")
    private String suite;

    @NotBlank(message = "City is required")
    @Schema(description = "City name", example = "New York")
    private String city;

    @NotBlank(message = "ZIP code is required")
    @Schema(description = "ZIP code", example = "10001")
    private String zipcode;

    @NotNull(message = "Geo coordinates are required")
    @Valid
    @Schema(description = "Geographic coordinates")
    private GeoDto geo;
} 