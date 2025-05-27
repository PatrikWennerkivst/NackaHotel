package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.BookingDTO;
import com.example.nackahotel.DTO.DetailedBookingDTO;
import com.example.nackahotel.Entity.Booking;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Repository.BookingRepository;
import com.example.nackahotel.Repository.CustomerRepository;
import com.example.nackahotel.Repository.RoomRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BookingService {

    private final RoomRepository roomRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;
    private final Mapper mapper;

    public List<DetailedBookingDTO> getAllBookings () {
        return bookingRepository.findAll().stream()
                .map(b -> mapper.bookingToDetailedBookingDTO(b))
                .toList();
    }

    public DetailedBookingDTO getBookingById (Long id) {
        return mapper.bookingToDetailedBookingDTO(
                bookingRepository.findById(id).orElse(null));
    }

    public BookingDTO getBookingDTOById(Long id) {
        return mapper.bookingToBookingDTO(
                bookingRepository.findById(id).orElse(null));
    }

    public DetailedBookingDTO createBooking (@Valid BookingDTO bookingDTO) {
        Customer customer = customerRepository
                .findById(bookingDTO.getCustomerId()).orElse(null);

        Room room = roomRepository
                .findById(bookingDTO.getRoomId()).orElse(null);

        // om rummet redan är bokat under någon del av det nya tiden
        for (Booking existingBooking : room.getBookings()) {
            if (datesOverlap(
                    bookingDTO.getStartDate(),
                    bookingDTO.getEndDate(),
                    existingBooking.getStartDate(),
                    existingBooking.getEndDate())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "The room is already booked. Try another date");
            }
        }

        try {
            Booking booking = new Booking(
                    bookingDTO.getStartDate(),
                    bookingDTO.getEndDate(),
                    bookingDTO.getNumberOfGuests(),
                    customer,
                    room);
            bookingRepository.save(booking);
            return mapper.bookingToDetailedBookingDTO(booking);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public DetailedBookingDTO updateBooking (Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if (bookingDTO.getStartDate() != null) booking.setStartDate(bookingDTO.getStartDate());
        if (bookingDTO.getEndDate() != null) booking.setEndDate(bookingDTO.getEndDate());

        if (bookingDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Customer with ID " + bookingDTO.getCustomerId() + " not found"));
            booking.setCustomer(customer);
        }
        if (bookingDTO.getRoomId() != null) {
            Room room = roomRepository.findById(bookingDTO.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Room with ID " + bookingDTO.getRoomId() + " not found"));
            booking.setRoom(room);
        }
        bookingRepository.save(booking);
        return mapper.bookingToDetailedBookingDTO(booking);
    }

    public List<DetailedBookingDTO> deleteBooking (Long id) {
        bookingRepository.deleteById(id);
        return bookingRepository.findAll().stream()
                .map(b -> mapper.bookingToDetailedBookingDTO(b))
                .toList();
    }

    // kolla om två datumintervall överlappar
    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    public boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found on this date."));

        for (Booking booking : room.getBookings()) {
            if (datesOverlap(startDate, endDate, booking.getStartDate(), booking.getEndDate())) {
                return false;
            }
        }
        return true;
    }

}
