package com.example.nackahotel.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(nullable = false)
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Room room;

    public Booking (LocalDate startDate, LocalDate endDate, Customer customer, Room room) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.room = room;
    }


}
