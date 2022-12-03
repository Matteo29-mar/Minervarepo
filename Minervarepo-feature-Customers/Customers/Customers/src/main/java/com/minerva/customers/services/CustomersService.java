package com.minerva.customers.services;

import com.minerva.customers.entities.Customers;

import java.util.List;

public interface CustomersService {

    //GET
    Customers getById(Long id);

    List<Customers> getAllCustomers();

    List<Customers> getByemail(String email);

    //POST
    Customers addCustomers(Customers newCustomers);

    //PUT
    Customers updateCustomers(Customers updatedCustomers);

    //DELETE
    boolean deleteCustomersById(Long id);


    void updateCustomer(Customers oldCustomer);

}
