package com.innowise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Geographic coordinates data transfer object")
public class GeoDto {
    @Schema(description = "Latitude coordinate", example = "-37.3159")
    private String lat;

    @Schema(description = "Longitude coordinate", example = "81.1496")
    private String lng;
} 