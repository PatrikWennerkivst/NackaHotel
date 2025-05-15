package com.example.nackahotel.Controller;

import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // http://localhost:8080/customers
    @RequestMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // http://localhost:8080/customers/delete/1
    @RequestMapping("/customers/delete/{customerId}")
    public String deleteCustomerIfNoBooking(@PathVariable Long customerId) {
        int deletedCount = customerRepository.deleteCustomerIfNoBookings(customerId);
        return (deletedCount > 0)
                ? ("Customer " + customerId + " deleted successfully")
                : ("Customer " + customerId + " is present in booking(s) and cannot be deleted");
    }

}
