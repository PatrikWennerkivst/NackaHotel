package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.DTO.DetailedBookingDTO;
import com.example.nackahotel.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @RequestMapping("/bookings")
    public String getAllBookings(Model model){
        List<DetailedBookingDTO> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "allBookings";
    }

    @RequestMapping("/bookings/{id}")
    public DetailedBookingDTO getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @PostMapping("/bookings/add")
    public DetailedBookingDTO addBooking(@Valid @RequestBody BookingDTO bookingDTO){
        return bookingService.createBooking(bookingDTO);
    }

    @RequestMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes){
        bookingService.deleteBooking(id);
        redirectAttributes.addFlashAttribute("message", "Booking " + id + " deleted successfully.");
        return "redirect:/bookings";
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


}
