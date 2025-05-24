package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.Entity.RoomType;
import com.example.nackahotel.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
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
    @ResponseBody
    public List<DetailedRoomDTO> getAllRooms() {
        return roomService.getAllRooms();
    }

    @RequestMapping("/rooms/{id}")
    @ResponseBody
    public DetailedRoomDTO getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    //http://localhost:8080/rooms/available/2025-05-14/2025-05-18/2
    @GetMapping("/rooms/available")
    @ResponseBody
    public String getAvailableRoomsPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) RoomType roomType,
            Model model) {

        if (startDate != null && endDate != null && roomType != null) {
            List<DetailedRoomDTO> availableRooms = roomService.getAllAvailableRooms(startDate, endDate);
            model.addAttribute("rooms", availableRooms);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("roomType", roomType);
        }

        model.addAttribute("roomTypes", RoomType.values());
        return "availableRooms.html";
    }

}
