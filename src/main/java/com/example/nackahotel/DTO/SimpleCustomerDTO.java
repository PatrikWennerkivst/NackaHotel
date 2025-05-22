package com.example.nackahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//used for a simpler customer output

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
}
