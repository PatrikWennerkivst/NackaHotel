package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.DTO.SimpleCustomerDTO;
import com.example.nackahotel.Entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class MapperTest {

    @Autowired
    private Mapper mapper;

    long customerId = 1L;
    String firstName = "John";
    String lastName = "Doe";
    String phoneNumber = "0700101010";
    String socialSecurityNumber = "1212121010";

    Customer customer = new Customer(customerId, firstName, lastName,
            socialSecurityNumber, phoneNumber, new ArrayList<>());

    @Test
    void customerToDetailedCustomerDTO() {
        DetailedCustomerDTO actual = mapper.customerToDetailedCustomerDTO(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
    }

    @Test
    void customerToSimpleCustomerDTO() {
        SimpleCustomerDTO actual = mapper.customerToSimpleCustomerDTO(customer);

        assertEquals(actual.getId(), customer.getId());
        assertEquals(actual.getFirstName(), customer.getFirstName());
    }

}