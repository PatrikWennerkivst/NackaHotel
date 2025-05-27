package com.example.nackahotel;

import com.example.nackahotel.Entity.Booking;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Entity.Room;
import com.example.nackahotel.Entity.RoomType;
import com.example.nackahotel.Repository.BookingRepository;
import com.example.nackahotel.Repository.CustomerRepository;
import com.example.nackahotel.Repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")

@Transactional // gör så att alla ändringar återställs efter körning
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Customer customerWithBooking;
    private Customer customerWithoutBooking;
    private Room room;
    private Booking booking;

    @BeforeEach
    public void init(){
        bookingRepository.deleteAll();
        customerRepository.deleteAll();
        roomRepository.deleteAll();

        customerWithBooking = new Customer();
        customerWithBooking.setFirstName("John");
        customerWithBooking.setLastName("Doe");
        customerWithBooking.setSocialSecurityNumber("0000000001");
        customerWithBooking.setPhoneNumber("0701234567");
        customerRepository.save(customerWithBooking);

        customerWithoutBooking = new Customer();
        customerWithoutBooking.setFirstName("Jane");
        customerWithoutBooking.setLastName("Doe");
        customerWithoutBooking.setSocialSecurityNumber("0000000002");
        customerWithoutBooking.setPhoneNumber("0701234568");
        customerRepository.save(customerWithoutBooking);

        room = new Room();
        room.setName("101");
        room.setType(RoomType.SINGLE);
        room.setMaxGuests(1);
        roomRepository.save(room);

        booking = new Booking();
        booking.setStartDate(LocalDate.parse("2025-10-06"));
        booking.setEndDate(LocalDate.parse("2025-10-15"));
        booking.setCustomer(customerWithBooking);
        booking.setRoom(room);
        bookingRepository.save(booking);
    }

    @Test
    public void testAddCustomer() throws Exception {

        long initialCount = customerRepository.count();
        mockMvc.perform(post("/customers/add")
                        .param("firstName", "Testing")
                        .param("lastName", "Testingson")
                        .param("socialSecurityNumber", "1234567890")
                        .param("phoneNumber", "0733355777"))
                .andExpect(status().is3xxRedirection());

        // kollar så att en customer har lagts till i db
        assertEquals(initialCount + 1, customerRepository.count());
    }

    @Test
    void editCustomer() throws Exception {
        Long customerId = customerWithBooking.getId();

        this.mockMvc.perform(post("/customers/edit/" + customerId)
                        .param("firstName", "Bob")
                        .param("lastName", "Smith"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"))
                .andExpect(flash().attribute("message", "Customer "
                                                        + customerId + " updated successfully."));


        assertThat(customerRepository.findById(customerId).get().getFirstName()).isEqualTo("Bob");
        assertThat(customerRepository.findById(customerId).get().getLastName()).isEqualTo("Smith");
    }

    @Test
    void showEditForm() throws Exception {
        Long customerId = customerWithBooking.getId();

        this.mockMvc.perform(get("/customers/edit/" + customerId))
                .andExpect(status().isOk())
                .andExpect(view().name("updateCustomer"));
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());

    }

    @Test
    void deleteCustomerIfNoBooking_withBooking() throws Exception {
        Long customerId = customerWithBooking.getId();
        this.mockMvc.perform(get("/customers/delete/" + customerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"))
                .andExpect(flash().attribute("error", "Customer "
                                                      + customerId + " could not be deleted (existing bookings)."));

        Optional<Customer> existingCustomer = customerRepository.findById(customerId);
        assertThat(existingCustomer).isPresent();
        assertThat(existingCustomer.get().getFirstName()).isEqualTo("John");
        assertThat(existingCustomer.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void deleteCustomerIfNoBooking_withoutBooking() throws Exception {
        Long customerId = customerWithoutBooking.getId();
        this.mockMvc.perform(get("/customers/delete/" + customerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"))
                .andExpect(flash().attribute("message", "Customer "
                                                        + customerId + " deleted successfully."));
    }

}