package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.DTO.DetailedBookingDTO;
import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.DTO.SimpleCustomerDTO;
import com.example.nackahotel.Service.BookingService;
import com.example.nackahotel.Service.CustomerService;
import com.example.nackahotel.Service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerService customerService;

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
    public String createBooking(@Valid @ModelAttribute BookingDTO bookingDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {

            DetailedRoomDTO room = roomService.getRoomById(bookingDTO.getRoomId());
            model.addAttribute("room", room);
            return "createBooking";
        }
        DetailedBookingDTO savedBooking = bookingService.createBooking(bookingDTO);
        model.addAttribute("booking", savedBooking);
        return "bookingConfirmation";
    }

    @GetMapping("/bookings/add")
    public String showCreateBookingForm(
            @RequestParam Long roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        // Hämta rummet
        DetailedRoomDTO room = roomService.getRoomById(roomId);

        List<SimpleCustomerDTO> simpleCustomerDTOS = customerService.getAllSimpleCustomers();

        // Skapar en ny BookingDTO med dom nya värdena
        BookingDTO bookingDTO = BookingDTO.builder()
                .roomId(roomId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        model.addAttribute("room", room);
        model.addAttribute("customers", simpleCustomerDTOS);
        model.addAttribute("bookingDTO", bookingDTO);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "createBooking";
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

        return "availableRooms";
    }

    @RequestMapping("/bookings/search")
    public String showFormBooking() {
        return "formBooking";
    }

}
