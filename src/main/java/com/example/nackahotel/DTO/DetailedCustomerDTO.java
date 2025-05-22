package com.example.nackahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//used for a detailed customer output

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedCustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private String phoneNumber;
    private List<SimpleBookingDTO> bookings;
}
