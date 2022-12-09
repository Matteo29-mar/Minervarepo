package com.minerva.customers.services;

import com.minerva.customers.entities.Customers;
import com.minerva.customers.repo.CustomersReposi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersServiceImpl implements CustomersService {

    private final List<Customers> empty = new ArrayList<>();
    private final  CustomersReposi repo;

    @Autowired
    public CustomersServiceImpl(CustomersReposi repo) {
        this.repo = repo;
    }

    @Override
    public Customers getById(Long id) {

        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find customer with id: " + id));

    }

    @Override
    public List<Customers> getAllCustomers() {

        List<Customers> customers = repo.findAll();

        if (customers.isEmpty()) {
            return empty;
        }
        return customers;
    }

    @Override
    public List<Customers> getByemail(String email) {

        List<Customers> customers = repo.findCustomersByemail(email);
        System.out.println(customers);
        if (!customers.isEmpty()) {
            return customers;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find customer with email: " + email);
        }
    }

    @Override
    public Customers addCustomers(Customers newCustomers) {
        return repo.save(newCustomers);
    }

    @Override
    public Customers updateCustomers(Customers updatedCustomers) {
        return repo.save(updatedCustomers);
    }

    @Override
    public boolean deleteCustomersById(Long id) {
        if( !repo.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find customer with id: " + id);
        repo.deleteById(id);
        return repo.findById(id).isEmpty();

    }

    @Override
    public void updateCustomer(Customers oldCustomer) {

    }
}
