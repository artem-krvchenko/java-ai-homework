package com.innowise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User data transfer object")
public class UserDto {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @NotBlank(message = "Name is required")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,50}$", message = "Username must be between 3 and 50 characters and can only contain letters, numbers, and underscores")
    @Schema(description = "Unique username", example = "johndoe")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address", example = "john.doe@example.com")
    private String email;

    @NotNull(message = "Address is required")
    @Valid
    @Schema(description = "User's address information")
    private AddressDto address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Invalid phone number format")
    @Schema(description = "Phone number", example = "+1-234-567-8900")
    private String phone;

    @NotBlank(message = "Website is required")
    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", message = "Invalid website URL format")
    @Schema(description = "Website URL", example = "https://johndoe.com")
    private String website;

    @NotNull(message = "Company is required")
    @Valid
    @Schema(description = "User's company information")
    private CompanyDto company;
} 