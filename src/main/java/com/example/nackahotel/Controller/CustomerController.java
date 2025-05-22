package com.example.nackahotel.Controller;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping("/customers")
    public List<DetailedCustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping("/customers/{id}")
    public DetailedCustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/customers/add")
    public DetailedCustomerDTO addCustomer(@Valid @RequestBody CreateCustomerDTO customer) {
        return customerService.createCustomer(customer);
    }

    @RequestMapping("/customers/delete/{customerId}")
    public String deleteCustomerIfNoBooking(@PathVariable Long customerId) {
        boolean deleted = customerService.deleteCustomerIfNoBooking(customerId);
        return (deleted)
                ? ("Customer " + customerId + " deleted successfully")
                : ("Customer " + customerId + " not found or present in booking(s) and cannot be deleted");
    }
}
