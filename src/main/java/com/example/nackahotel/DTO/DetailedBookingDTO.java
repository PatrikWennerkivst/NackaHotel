package com.example.nackahotel.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DetailedBookingDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private CustomerDTO customer;
    private RoomDTO room;

}
