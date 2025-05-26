package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.Entity.RoomType;
import com.example.nackahotel.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
//@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @RequestMapping("/rooms")
    public String getAllRooms(Model model) {
        List<DetailedRoomDTO> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "allRooms";
    }

    @RequestMapping("/rooms/{id}")
    public DetailedRoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    //http://localhost:8080/rooms/available/2025-05-14/2025-05-18/2
    @GetMapping("/rooms/available")
    @ResponseBody
    public String getAvailableRooms(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) Integer maxGuests,
            Model model) {

        if (startDate != null && endDate != null && roomType != null && maxGuests != null) {
            List<DetailedRoomDTO> availableRooms = roomService.getAllAvailableRooms(startDate, endDate);

            List<DetailedRoomDTO> filterdRooms = availableRooms.stream()
                    .filter(room -> room.getType() == roomType)
                    .filter(room -> room.getMaxGuests() >= maxGuests)
                    .toList();
            model.addAttribute("rooms", availableRooms);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("roomType", roomType);
            model.addAttribute("maxGuests", maxGuests);

        }

        model.addAttribute("roomTypes", RoomType.values());
        return "availableRooms.html";
    }

}
