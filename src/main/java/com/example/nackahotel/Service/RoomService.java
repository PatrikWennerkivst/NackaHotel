package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.DetailedRoomDTO;
import com.example.nackahotel.Entity.RoomType;
import com.example.nackahotel.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final Mapper mapper;

    public List<DetailedRoomDTO> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(r -> mapper.roomToDetailedRoomDTO(r))
                .toList();
    }

    public DetailedRoomDTO getRoomById(Long id) {
        return mapper.roomToDetailedRoomDTO(
                roomRepository.findById(id).orElse(null));
    }

    public List<DetailedRoomDTO> getAllAvailableRooms(LocalDate startDate, LocalDate endDate) {
        return roomRepository.findAllAvailableRooms(startDate, endDate)
                .stream()
                .map(r -> mapper.roomToDetailedRoomDTO(r))
                .toList();
    }
}
