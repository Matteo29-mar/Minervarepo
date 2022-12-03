package com.minerva.customers.integration;

import com.minerva.customers.entities.Customers;
import com.minerva.customers.repo.CustomersReposi;
import com.minerva.customers.services.CustomersService;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@Slf4j
@RequestMapping("api/v1")
public class CustomersController {

    private final CustomersService service;
    @Autowired
    private final CustomersReposi customerRepository;

    private Long idFound = 0L;


    public CustomersController(CustomersService service, CustomersReposi customerRepository) {
        this.service = service;
        this.customerRepository = customerRepository;
    }
    @GetMapping("/customers")
    public List<Customers> getAll() {
        return service.getAllCustomers();
    }
    // CREATE
    @RequestMapping(method = RequestMethod.POST)
    public Customers addCustomers(@Valid @RequestBody Customers customer) {
        return customerRepository.save(customer);
    }


    // READ
    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customers getCustomers(@PathVariable Long customersid) {
        Optional<Customers> customerOptional = customerRepository.findById(customersid);
        if(customerOptional.isPresent())
            return customerOptional.get();
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Customers> getAllCustomers() {
        log.info("Get all customers");
        return customerRepository.findAll();
    }


    // UPDATE
    @RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
    public Customers updateCUstomers(@RequestBody Customers customer, @RequestBody String customerId ) {
        return customerRepository.save(customer);
    }


    // DELETE
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
    public void deleteCustomers(@PathVariable Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
