package com.example.nackahotel.DTO;

import com.example.nackahotel.Entity.RoomType;
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
    private RoomType type;
    private int maxGuests; //lagt till för max antal gäster
    private int maxExtraBeds; //lagt till för mac antal bäddar
    private List<SimpleBookingDTO> bookings;

}
