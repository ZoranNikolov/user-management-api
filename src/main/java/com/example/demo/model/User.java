package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "23", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "First name of the user", example = "Neo")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Anderson")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(description = "User's date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Contact phone number", example = "+1234567890")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
    @Size(min = 7, max = 15, message = "Phone number length must be between 7 and 15 digits")
    private String phoneNumber;

    @Schema(description = "Email address of the user", example = "neo.anderson@matrix.com")
    @NotBlank(message = "Email address is required")
    @Email(message = "Email should be valid")
    private String emailAddress;

}
