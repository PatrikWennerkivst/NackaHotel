package com.example.nackahotel.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @NotEmpty(message = "First name is required")
    @Size(min = 2, message = "At least 2 characters")
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    @NotEmpty(message = "Last name is required")
    @Size(min = 2, message = "At least 2 characters")
    private String lastName;

    @NotBlank
    @Column(nullable = false)
    @NotEmpty(message = "Social security number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Must be 10 digits")
    private String socialSecurityNumber;

    @NotBlank
    @Column(nullable = false)
    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @OneToMany (mappedBy = "customer")
    @JsonManagedReference
    private List<Booking> bookings = new ArrayList<>();

    public Customer(String firstName, String lastName, String socialSecurityNumber, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.phoneNumber = phoneNumber;
    }
}
