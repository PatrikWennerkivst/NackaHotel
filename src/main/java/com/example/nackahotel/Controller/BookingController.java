package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.Entity.Booking;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Repository.BookingRepository;
import com.example.nackahotel.Repository.CustomerRepository;
import com.example.nackahotel.Repository.RoomRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
public class BookingController {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;

    BookingController(BookingRepository bookingRepository,
                      CustomerRepository customerRepository,
                      RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    @RequestMapping("/bookings")
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    @RequestMapping("/bookings/{id}")
    public Booking getBooking(@PathVariable Long id){
        return bookingRepository.findById(id).orElse(null);
    }

    @PostMapping("/bookings/add")
    public List<Booking> addBookingPOST(@Valid @RequestBody BookingDTO bookingDTO){

        Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        Booking booking = new Booking(bookingDTO.getStartDate(), bookingDTO.getEndDate(),
                customer, room);

        bookingRepository.save(booking);
        return bookingRepository.findAll();
    }

    @RequestMapping("/bookings/delete/{id}")
    public List<Booking> deleteBooking(@PathVariable Long id){
        bookingRepository.deleteById(id);
        return bookingRepository.findAll();
    }

    @PutMapping("/bookings/update/{id}")
    public List<Booking> updateBookingPut(@PathVariable Long id,
                                          @Valid @RequestBody BookingDTO updateRequest){

        Booking bookingToUpdate = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if (updateRequest.getStartDate() != null) bookingToUpdate.setStartDate(updateRequest.getStartDate());
        if (updateRequest.getEndDate()   != null) bookingToUpdate.setEndDate(updateRequest.getEndDate());

        if (updateRequest.getRoomId() != null) {
            Room room = roomRepository.findById(updateRequest.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
            bookingToUpdate.setRoom(room);
        }
        bookingRepository.save(bookingToUpdate);
        return bookingRepository.findAll();
    }


}
