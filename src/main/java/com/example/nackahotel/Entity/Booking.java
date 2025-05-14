package com.example.nackahotel.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(nullable = false)
    private LocalDate startDate;

    @NotBlank
    @Column(nullable = false)
    private LocalDate endDate;

    @NotBlank
    @ManyToOne
    private Customer customer;

    @NotBlank
    @ManyToOne
    private Room room;


}
