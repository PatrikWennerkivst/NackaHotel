package com.example.nackahotel.DTO;

import com.example.nackahotel.Entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Long id;
    private String name;
    private RoomType type;


}
