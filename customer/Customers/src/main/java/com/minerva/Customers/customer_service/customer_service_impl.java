package com.minerva.Customers.customer_service;

import com.minerva.Customers.customer_entitiy.Customers;
import com.minerva.Customers.customer_repository.customer_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class customer_service_impl implements CustomerService {

    private final List<Customers> empty = new ArrayList<>();

    private final customer_repository repository;
    private Long Email;
    private Customers newCustomers;
    private Customers updateCustomers;

    @Autowired
    public customer_service_impl(customer_repository repository) {
        this.repository = repository;
    }

    @Override
    public Customers getById(Long id) {
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, " Cannot find customers with this id:" + id));
    }

    @Override
    public List<Customers> getAllCustomers() {

        List<Customers> customers = repository.findAll();
        if (customers.isEmpty()) {
            return empty;
           
        } 
        return customers;
    }
//meglio email perchè oltre all' id è molto più unica
    @Override    
        public List<Customers> getByEmail(String Email) {
       List<Customers> customers1 = repository.findCustomersByEmail(Email);
       System.out.println(customers1);

       if(!customers1.isEmpty()){
           return customers1;
       }else{
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find customer with this email:" + Email);
       }
    }

    @Override
    public Customers addCustomers(Customers newCustomers) {
        return repository.save(newCustomers);
    }


    @Override
    public Customers updateCustomers(Customers updateCostumers) {
        return repository.save(updateCustomers);
    }

    @Override
    public boolean deleteCustomersById(Long id) {
            if( !repository.findById(id).isPresent())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find customer with this id:" + id);
            repository.deleteById(id);
        return repository.findById(id).isEmpty();
    }
    

}