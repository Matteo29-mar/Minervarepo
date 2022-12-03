package com.minerva.customers.repo;

import com.minerva.customers.entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomersReposi extends JpaRepository<Customers, Long> {

    @Query("select b from customers b where b.email like ?")
    List<Customers> findCustomersByemail(@NonNull String email);

}
