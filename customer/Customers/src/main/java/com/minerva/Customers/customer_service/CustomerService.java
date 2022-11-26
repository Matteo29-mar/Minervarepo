package com.minerva.Customers.customer_service;

import com.minerva.Customers.customer_entitiy.Customers;
import org.springframework.beans.factory.config.CustomEditorConfigurer;

import java.util.List;


public interface CustomerService {



    //GET
    Customers getById( Long id);
    List<Customers> getAllCustomers();


    //POST

    //meglio email perchè oltre all' id è molto più unica che il nome
    List<Customers> getByEmail(String Email);

    Customers addCustomers(Customers newCustomers);
    //UPDATE

    Customers updateCustomers(Customers updateCostumers);
    //DELETE

    boolean deleteCustomersById(Long id);
}

