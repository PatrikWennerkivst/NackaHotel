package com.example.nackahotel.Controller;

import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Repository.RoomRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    //Exempel:
    //http://localhost:8080/rooms/available/2025-05-14/2025-05-18/2
    @RequestMapping("/rooms/available/{startDate}/{endDate}/{roomType}")
    public List<Room> getAvailableRooms(@PathVariable LocalDate startDate,
                                        @PathVariable LocalDate endDate,
                                        @PathVariable int roomType) {
        return roomRepository.findAvailableRooms(startDate, endDate, roomType);
    }

}
