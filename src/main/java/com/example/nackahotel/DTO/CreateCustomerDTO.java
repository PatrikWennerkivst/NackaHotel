package com.example.nackahotel.DTO;

//used for input from user

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO {

    @NotEmpty(message = "First name is required")
    @Size(min = 2, message = "At least 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    @Size(min = 2, message = "At least 2 characters")
    private String lastName;

    @NotEmpty(message = "Social security number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Must be 10 digits")
    private String socialSecurityNumber;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

}
