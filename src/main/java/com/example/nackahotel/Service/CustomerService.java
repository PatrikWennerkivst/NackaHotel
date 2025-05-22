package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.CreateCustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.Entity.Customer;
import com.example.nackahotel.Repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
