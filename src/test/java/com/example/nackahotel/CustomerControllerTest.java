package com.example.nackahotel;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.Repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties") // för att
@Transactional // gör så att alla ändringar återställs efter körning
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCustomer() throws Exception {

        long initialCount = customerRepository.count();

        CreateCustomerDTO customerData = new CreateCustomerDTO(
                "Testing",
                "Testingson",
                "1234567890",
                "0733355777"
        );

        // gör om DTO till JSON
        String customerJson = objectMapper.writeValueAsString(customerData);

        mockMvc.perform(post("/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("\"firstName\":\"Testing\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Testingson\"")))
                .andExpect(content().string(containsString("\"socialSecurityNumber\":\"1234567890\"")))
                .andExpect(content().string(containsString("\"phoneNumber\":\"0733355777\"")))
                .andExpect(content().string(containsString("\"id\":"))) // Verifierar att ID finns
                .andExpect(content().string(containsString("\"bookings\":[]"))); // Tom bookings-array

        // kollar så att en customer har lagts till i db
        assertEquals(initialCount + 1, customerRepository.count());
    }
}