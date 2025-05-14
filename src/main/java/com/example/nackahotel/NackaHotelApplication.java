package com.example.nackahotel;

import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
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
    public CommandLineRunner customer(CustomerRepository repository) {
        return args -> {
            repository.save(new Customer("Maria", "Kirkou", "000000000000", "0701234567"));
            repository.save(new Customer("Jessica", "Sundin", "000000000000", "0701234567"));
            repository.save(new Customer("Patrik", "Wennerkvist", "000000000000", "0701234567"));
            repository.save(new Customer("Melina", "Skytén", "000000000000", "0701234567"));
        };
    }

}
