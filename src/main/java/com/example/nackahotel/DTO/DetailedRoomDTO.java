package com.example.nackahotel.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedRoomDTO {

    private Long id;
    private String name;
    private int type;
    private List<SimpleBookingDTO> bookings;

}
