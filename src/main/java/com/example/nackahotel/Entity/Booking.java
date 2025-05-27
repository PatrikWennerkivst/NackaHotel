package com.example.nackahotel.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private int extraBeds = 0;

    @Column(nullable = false)
    private int numberOfGuests;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Room room;

    public Booking(LocalDate startDate, LocalDate endDate, int numberOfGuests, Customer customer, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.customer = customer;
        this.room = room;

        int bedCapacity;
        if (room.getType() == RoomType.SINGLE) {
            bedCapacity = 1;
        } else if (room.getType() == RoomType.DOUBLE) {
            bedCapacity = 2;
        } else if (room.getType() == RoomType.SUITE) {
            bedCapacity = 4;
        } else {
            bedCapacity = 1;
        }
        int extraNeeded = Math.max(0, numberOfGuests - bedCapacity);

        // Enkel validering för dubbelrum
        if (room.getType() == RoomType.DOUBLE && extraNeeded > 2) {
            throw new IllegalArgumentException("Dubbelrum kan max ha 2 extrasängar");
        }

        this.extraBeds = extraNeeded;
    }
}