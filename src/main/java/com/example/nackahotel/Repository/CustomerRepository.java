package com.example.nackahotel.Repository;

import com.example.nackahotel.Entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @Transactional
    @Query("""
            DELETE FROM Customer c
            WHERE c.id=:customerId
            AND NOT EXISTS (
            SELECT 1 FROM Booking b WHERE b.customer.id= c.id
            )
            """)
    int deleteCustomerIfNoBookings(@Param("customerId") Long customerId);

}
