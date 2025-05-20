package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("/rooms")
    public List<DetailedRoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @RequestMapping("/rooms/{id}")
    public DetailedRoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    //http://localhost:8080/rooms/available/2025-05-14/2025-05-18/2
    @RequestMapping("/rooms/available/{startDate}/{endDate}/{roomType}")
    public List<DetailedRoomDTO> getAvailableRooms(@PathVariable LocalDate startDate,
                                                   @PathVariable LocalDate endDate,
                                                   @PathVariable int roomType) {
        return roomService.getAvailableRooms(startDate, endDate, roomType);
    }

}
