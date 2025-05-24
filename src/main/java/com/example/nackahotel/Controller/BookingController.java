package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.DTO.DetailedBookingDTO;
import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Service.BookingService;
import com.example.nackahotel.Service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
//@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final RoomService roomService;

    @RequestMapping("/bookings")
    @ResponseBody
    public List<DetailedBookingDTO> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @RequestMapping("/bookings/{id}")
    public DetailedBookingDTO getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    /*@PostMapping("/bookings/add")
    public DetailedBookingDTO addBooking(@Valid @RequestBody BookingDTO bookingDTO){
        return bookingService.createBooking(bookingDTO);
    }

     */
    @PostMapping("/bookings/add")
    public String createBooking(@Valid @ModelAttribute BookingDTO bookingDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {

            DetailedRoomDTO room = roomService.getRoomById(bookingDTO.getRoomId());
            model.addAttribute("room", room);
            return "createBooking";
        }
        return "redirect:/bookings";
    }

    @GetMapping("/bookings/add")
    public String showCreateBookingForm(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        // Hämta rummet
        DetailedRoomDTO room = roomService.getRoomById(roomId);

        // Skapar en ny BookingDTO med dom nya värden
        BookingDTO bookingDTO = BookingDTO.builder()
                .roomId(roomId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        model.addAttribute("room", room);
        model.addAttribute("bookingDTO", bookingDTO);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "createBooking";
    }
    @RequestMapping("/bookings/delete/{id}")
    public List<DetailedBookingDTO> deleteBooking(@PathVariable Long id){
        return bookingService.deleteBooking(id);
    }

    @PutMapping("/bookings/update/{id}")
    public DetailedBookingDTO updateBooking(@PathVariable Long id,
                                            @Valid @RequestBody BookingDTO updateRequest){
        return bookingService.updateBooking(id, updateRequest);
    }

//    @GetMapping("/bookings/check") //kollar om rummet redan är bokat elr itt (kopplat till overlapping)
//    public ResponseEntity<String> checkAvailability(
//            @RequestParam Long roomId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//
//        boolean isAvailable = bookingService.isRoomAvailable(roomId, startDate, endDate);
//
//        if (isAvailable) {
//            return ResponseEntity.ok("The room is available.");
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("The room is already booked.");
//        }
//    }

    // Visar alla bookingar som HTML

    // Visar lediga rum för booking
    @GetMapping("/booking/rooms")
    public String getAvailableRoomsForBooking(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer maxGuests,
            Model model) {

        List<DetailedRoomDTO> availableRooms = roomService.getAllAvailableRooms(startDate, endDate);
        if (maxGuests != null) {
            availableRooms = availableRooms.stream()
                    .filter(room -> room.getMaxGuests() >= maxGuests)
                    .toList();
        }
        model.addAttribute("rooms", availableRooms);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "availableRooms.html";
    }
}
