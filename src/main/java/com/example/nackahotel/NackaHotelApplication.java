package com.example.nackahotel;

import com.example.nackahotel.Entity.Booking;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Entity.RoomType;
import com.example.nackahotel.Repository.BookingRepository;
import com.example.nackahotel.Repository.CustomerRepository;
import com.example.nackahotel.Repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class NackaHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NackaHotelApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner customer(RoomRepository roomRepository,
                                      CustomerRepository customerRepository,
                                      BookingRepository bookingRepository) {
        return args -> {
            Room room1 = new Room("101", RoomType.SINGLE, 1);
            Room room2 = new Room("102", RoomType.DOUBLE, 2);
            Room room3 = new Room("103", RoomType.SUITE, 4);
            Room room4 = new Room("104", RoomType.SINGLE, 1);
            Room room5 = new Room("201", RoomType.DOUBLE, 2);
            Room room6 = new Room("202", RoomType.SUITE, 4);
            Room room7 = new Room("203", RoomType.SINGLE, 1);
            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            roomRepository.save(room4);
            roomRepository.save(room5);
            roomRepository.save(room6);
            roomRepository.save(room7);

            Customer melina = new Customer("Melina", "Skyten",
                    "0000000001", "0701234567");
            Customer maria = new Customer("Maria", "Kirkou",
                    "0000000002", "0701234567");
            Customer jessica = new Customer("Jessica", "Sundin",
                   "0000000003", "0701234567");
            Customer patrik = new Customer("Patrik", "Wennerkvist",
                   "0000000004", "0701234567");
            customerRepository.save(melina);
            customerRepository.save(maria);
            customerRepository.save(jessica);
            customerRepository.save(patrik);

            Booking booking1 = new Booking(LocalDate.parse("2025-12-15"),
                  LocalDate.parse("2025-12-18"), melina, room1);
            Booking booking2 = new Booking(LocalDate.parse("2025-12-19"),
                    LocalDate.parse("2025-12-22"), maria, room1);
            Booking booking3 = new Booking(LocalDate.parse("2025-12-12"),
                    LocalDate.parse("2025-12-15"), jessica, room2);
            Booking booking4 = new Booking(LocalDate.parse("2025-12-20"),
                    LocalDate.parse("2025-12-25"), patrik, room5);
            bookingRepository.save(booking1);
            bookingRepository.save(booking2);
            bookingRepository.save(booking3);
            bookingRepository.save(booking4);
      };
   }
}
