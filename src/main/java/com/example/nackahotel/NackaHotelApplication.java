package com.example.nackahotel;

import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Repository.CustomerRepository;
import com.example.nackahotel.Repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NackaHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NackaHotelApplication.class, args);
    }

    @Bean
    public CommandLineRunner customer(CustomerRepository repository, RoomRepository roomRepository) {
        return args -> {
            repository.save(new Customer("Maria", "Kirkou", "000000000000", "0701234567"));
            repository.save(new Customer("Jessica", "Sundin", "000000000000", "0701234567"));
            repository.save(new Customer("Patrik", "Wennerkvist", "000000000000", "0701234567"));
            repository.save(new Customer("Melina", "Skytén", "000000000000", "0701234567"));

            roomRepository.save(new Room("101", 1));
            roomRepository.save(new Room("102", 2));
            roomRepository.save(new Room("103", 1));
            roomRepository.save(new Room("104", 1));
            roomRepository.save(new Room("201", 2));
            roomRepository.save(new Room("202", 1));
            roomRepository.save(new Room("203", 2));
        };
    }

}
