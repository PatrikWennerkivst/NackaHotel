package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.DTO.DetailedBookingDTO;
import com.example.nackahotel.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/bookings/update/{id}")
    public String updateBooking(@PathVariable Long id,
                                @Valid @ModelAttribute("booking") BookingDTO updateRequest,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("booking", updateRequest);
            return "updateBooking";
        }

        try {
            bookingService.updateBooking(id, updateRequest);
            redirectAttributes.addFlashAttribute("message",
                    "Booking " + id + " updated successfully.");
        } catch (ResponseStatusException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getReason());
        }
        return "redirect:/bookings";
    }

    @GetMapping("/bookings/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        BookingDTO booking = bookingService.getBookingDTOById(id);
        model.addAttribute("booking", booking);
        return "updateBooking";
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
