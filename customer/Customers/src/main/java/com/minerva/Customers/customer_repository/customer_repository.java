package com.minerva.Customers.customer_repository;

import com.minerva.Customers.customer_entitiy.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface customer_repository  extends JpaRepository<Customers, Long> {
    @Query("select a from Customers a where a.Id ?1")
    List<Customers> findCustomersById(@NonNull Long Id);

    List<Customers> findCustomersByEmail(String email);
}
