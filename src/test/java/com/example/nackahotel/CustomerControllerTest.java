package com.example.nackahotel;

import com.example.nackahotel.Repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional // gör så att alla ändringar återställs efter körning
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testAddCustomer() throws Exception {

        long initialCount = customerRepository.count();

        mockMvc.perform(post("/customers/add")
                        .param("firstName", "Testing")
                        .param("lastName", "Testingson")
                        .param("socialSecurityNumber", "1234567890")
                        .param("phoneNumber", "0733355777"))
                .andExpect(status().isOk());

        // kollar så att en customer har lagts till i db
        assertEquals(initialCount + 1, customerRepository.count());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());

    }


}