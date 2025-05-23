package com.example.nackahotel.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING) // så den sparas som "SINGLE" eller "DOUBLE" i själva databasen
    @Column(nullable = false)
    private RoomType type;


    private int maxGuests;

    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Booking> bookings;

    public Room(String name, RoomType type, int maxGuests) {
        this.name = name;
        this.type = type;
        this.maxGuests = maxGuests;
    }
}

