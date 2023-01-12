package com.minerva.customers.integration;

import com.minerva.customers.entities.Customers;
import com.minerva.customers.repo.CustomersReposi;
import com.minerva.customers.services.CustomersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class CustomersController {

    private final CustomersService service;
    @Autowired
    private final CustomersReposi customerRepository;

    public CustomersController(CustomersService service, CustomersReposi customerRepository) {
        this.service = service;
        this.customerRepository = customerRepository;
    }


    // CREATE
    @RequestMapping(value ="/customer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customers> addCustomers(@Valid @RequestBody Customers newCustomers) {
        if (newCustomers == null) // throw badRequest if body doesn't contain a Customers Obj
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer cannot be null");
        Customers customer = service.addCustomers(newCustomers);
        if (customer == null) // throws internalError if customer was not added
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested Customers");
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


    // READ
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customers> getCustomers(@PathVariable Long id) {
        if (id == null) // throw badRequest if Customers id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        Customers found = service.getById(id);
        if (found == null) // throw notFound if requested Customers doesn't exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find Customers with id: " + id);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Customers> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll();
    }

    // UPDATE
    @RequestMapping(value = "/{customersId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Customers> updateCustomer(@PathVariable Long customersId, @RequestBody Customers updatedCustomer) {
        if (customersId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id  cannot be null");
        Customers oldCustomer = service.getById(customersId);
        if (oldCustomer != null) {
            if(updatedCustomer.getNome() != null) oldCustomer.setNome(updatedCustomer.getNome());
            if(updatedCustomer.getCognome() != null) oldCustomer.setCognome(updatedCustomer.getCognome());
            if(updatedCustomer.getEmail() != null) oldCustomer.setEmail(updatedCustomer.getEmail());
            if(updatedCustomer.getTelefono() != null) oldCustomer.setTelefono(updatedCustomer.getTelefono());
            if(updatedCustomer.getData_nascita() != null) oldCustomer.setData_nascita(updatedCustomer.getData_nascita());

            service.updateCustomers(oldCustomer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.NO_CONTENT);
        } else { // throws notFound if the Customer to update does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find Customer with id: " + customersId);
        }
    }


    // DELETE
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAllCustomers() {
        customerRepository.deleteAll();
        log.info("Delete all customers");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{customersid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomers(@PathVariable Long customersid) {
        customerRepository.deleteById(customersid);
        log.info("Delete customer with id" + customersid);
    }
}
