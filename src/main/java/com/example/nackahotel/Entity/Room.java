package com.example.nackahotel.Entity;

import jakarta.persistence.*;
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

    // Denna kanske inte behövs gör så att man
    // enkelt kan kolla bokningar per rum
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

}
