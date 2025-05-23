package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.*;
import com.example.nackahotel.Entity.Booking;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Component
public class Mapper {

    public SimpleBookingDTO bookingToSimpleBookingDTO(Booking booking) {
        return SimpleBookingDTO.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    public BookingDTO bookingToBookingDTO(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    public DetailedBookingDTO bookingToDetailedBookingDTO (Booking booking) {
        return DetailedBookingDTO.builder()
                .id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(customerToCustomerDTO(booking.getCustomer()))
                .room(roomToRoomDTO(booking.getRoom()))
                .build();
    }

    public SimpleCustomerDTO customerToCustomerDTO(Customer customer) {
        return SimpleCustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

    public DetailedCustomerDTO customerToDetailedCustomerDTO(Customer customer) {
        return DetailedCustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .socialSecurityNumber(customer.getSocialSecurityNumber())
                .phoneNumber(customer.getPhoneNumber())
                .bookings(customer.getBookings().stream()
                        .map(b-> bookingToSimpleBookingDTO(b)).toList())
                .build();
    }

    public SimpleRoomDTO roomToRoomDTO(Room room) {
        return SimpleRoomDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .build();
    }

    public DetailedRoomDTO roomToDetailedRoomDTO(Room room) {
        return DetailedRoomDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .maxGuests(room.getMaxGuests())
                .bookings(room.getBookings().stream()
                        .map(b -> bookingToSimpleBookingDTO(b)).toList())
                .build();
    }

}
