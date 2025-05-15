package com.example.nackahotel.Repository;

import com.example.nackahotel.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            SELECT r FROM Room r
            WHERE r.type = :roomType
            AND NOT EXISTS (
            SELECT 1 FROM Booking b
            WHERE b.room.id = r.id
            AND b.startDate < :endDate
            AND b.endDate > :startDate
            )
            """)
    public List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate,
                                         @Param("roomType") int roomType);

}
