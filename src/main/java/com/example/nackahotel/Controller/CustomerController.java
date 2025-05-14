package com.example.nackahotel.Controller;

import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
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

    @PostMapping("/customers/delete")
    public List<Customer> deleteCustomer(@RequestBody Customer customer) {
        customerRepository.delete(customer);
        return customerRepository.findAll();
    }

}
