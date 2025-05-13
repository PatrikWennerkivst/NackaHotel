package com.example.nackahotel.Repository;

import com.example.nackahotel.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {



}
