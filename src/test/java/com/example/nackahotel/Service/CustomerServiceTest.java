package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.DTO.SimpleCustomerDTO;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.NackaHotelApplication;
import com.example.nackahotel.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = NackaHotelApplication.class)
class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    private Long customerId;
    private String customerName;
    private String lastName;
    private String ssn;
    private String phoneNumber;

    private Customer customer;
    private CreateCustomerDTO createCustomerDTO;
    private DetailedCustomerDTO detailedCustomerDTO;

    @BeforeEach
    void init() {
        //testdatan
        customerId = 1L;
        customerName = "Anna";
        lastName = "Andersson";
        ssn = "1990010112";
        phoneNumber = "0701234567";

        customer = new Customer(customerName, lastName, ssn, phoneNumber);
        createCustomerDTO = new CreateCustomerDTO(customerName, lastName, ssn, phoneNumber);

        detailedCustomerDTO = DetailedCustomerDTO.builder()
                .id(customerId)
                .firstName(customerName)
                .lastName(lastName)
                .socialSecurityNumber(ssn)
                .phoneNumber(phoneNumber)
                .build();
    }

    @Test
    void deleteCustomerThatDoesNotExist_ShouldReturnFalse() {
        boolean result = customerService.deleteCustomerIfNoBooking(99999L);
        assertFalse(result);
    }

    @Test
    void getAllSimpleCustomers_ReturnsListOfDTOs() {
        //Denna kund sparas direkt i databasen
        Customer c1 = new Customer("Eva", "Karlsson", "199005052222", "0709988776");
        customerRepository.save(c1);

        List<SimpleCustomerDTO> customers = customerService.getAllSimpleCustomers();

        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertTrue(customers.stream().anyMatch(c -> c.getFirstName().equals("Eva")));
    }
}