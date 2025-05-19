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
    @NotNull(message = "Room ID is required")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private int type;

    // Denna kanske inte behövs gör så att man
    // enkelt kan kolla bokningar per rum
    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<Booking> bookings;

    public Room(String name, int type) {
        this.name = name;
        this.type = type;
    }

}
