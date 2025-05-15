package com.example.nackahotel.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    @NotEmpty(message = "please write your name")
    @Size(min = 3,message = "at least 3 characters ")
    private String name;

    @NotEmpty(message = "Email required")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Phone number required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

}
