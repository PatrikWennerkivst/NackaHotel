package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.DTO.SimpleCustomerDTO;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Mapper mapper;

    public List<DetailedCustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(c -> mapper.customerToDetailedCustomerDTO(c))
                .toList();
    }

    public DetailedCustomerDTO getCustomerById(Long id) {
        return mapper.customerToDetailedCustomerDTO(
                customerRepository.findById(id).orElse(null));
    }

    public DetailedCustomerDTO createCustomer(@Valid CreateCustomerDTO customer) {
        Customer newCustomer = new Customer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getSocialSecurityNumber(),
                customer.getPhoneNumber());
        Customer savedCustomer = customerRepository.save(newCustomer);
        return mapper.customerToDetailedCustomerDTO(savedCustomer);
    }

    public boolean deleteCustomerIfNoBooking(Long customerId) {
        int deletedCount = customerRepository.deleteCustomerIfNoBookings(customerId);
        return deletedCount > 0;
    }

//    public String updateCustomer(Long id, String firstName, String lastName,
//                                 String socialSecurityNumber, String phoneNumber) {
//        return customerRepository.findById(id).map(customer -> {
//            if (firstName != null) customer.setFirstName(firstName);
//            if (lastName != null) customer.setLastName(lastName);
//            if (socialSecurityNumber != null) customer.setSocialSecurityNumber(socialSecurityNumber);
//            if (phoneNumber != null) customer.setPhoneNumber(phoneNumber);
//
//            customerRepository.save(customer);
//            return "Customer " + id + " has been updated";
//        } )
//                .orElse("Customer " + id + " was not found");
//    }

    public void updateCustomer (DetailedCustomerDTO customer) {
        Customer existingCustomer = customerRepository.findById(customer.getId()).orElse(null);

        if (existingCustomer != null) {
            if (customer.getFirstName() != null) existingCustomer.setFirstName(customer.getFirstName());
            if (customer.getFirstName() != null) existingCustomer.setLastName(customer.getLastName());
            if (customer.getFirstName() != null) existingCustomer.setSocialSecurityNumber(customer.getSocialSecurityNumber());
            if (customer.getFirstName() != null) existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            customerRepository.save(existingCustomer);
        }
    }

}
