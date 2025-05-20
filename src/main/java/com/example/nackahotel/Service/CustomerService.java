package com.example.nackahotel.Service;

import com.example.nackahotel.DTO.CustomerDTO;
import com.example.nackahotel.DTO.DetailedCustomerDTO;
import com.example.nackahotel.Entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final BookingService bookingService;



}
