package com.example.nackahotel.Controller;

import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping("/customers/add")
    public List<Customer> addCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customerRepository.findAll();
    }

    @RequestMapping("/customers/delete/{customerId}")
    public String deleteCustomerIfNoBooking(@PathVariable Long customerId) {
        int deletedCount = customerRepository.deleteCustomerIfNoBookings(customerId);
        return (deletedCount > 0)
                ? ("Customer " + customerId + " deleted successfully")
                : ("Customer " + customerId + " is present in booking(s) and cannot be deleted");
    }

}
