package com.minerva.Customers.customer_controller;

import com.minerva.Customers.customer_entitiy.Customers;
import com.minerva.Customers.customer_service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


/*
controllo microservizio, in questa parte ci sono le crud.
 ottenimento (get) abbiamo un getall e un get capillare. Nel gert all chiamimao tuti i customer. Nel get capillare andiamo prima alla ricerca del singolo e si valuta se c'è o meno
 create(add) in questa parte andiamo alla creazione di un nuovo customer, controlliamo se id sia univoco e poi si procede con la nuova creazione...
 update, qui aggiorniamo id da uno vecchio con uno nuovo, controlliamo pure se il nuovo è diverso da quello vecchio
 delete, eliminiamo id che non ci serve
 */
@RestController
@RequestMapping("api/v1")
public class CustomerController {

    private final CustomerService service;

    private Long idFound = 0L;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customers")
    public List<Customers> getAll() {
        return service.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    public Customers getCustomers(@PathVariable Long id) {
        if (id == null) // se  vero ricerca un id di un customer
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        Customers found = service.getById(id);
        if (found == null) // se  id trovato è un null vuol dire che id non esiste
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find customer with id: " + id);
        return found;
    }


    @PostMapping("customers")
    @ResponseStatus(HttpStatus.CREATED)
    public Customers addCustomers( @RequestBody Customers newCustomers) {
        if (newCustomers == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customers  cannot be null");
        Customers customers = service.addCustomers(newCustomers);
        if (customers == null) // throws internalError if book was not added
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested customer");
        return customers;
    }

    @PutMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Customers updateCustomers (@PathVariable Long id, @RequestBody Customers updateCustomers) {
        if (id == null)  // throw badRequest if book id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id customer cannot be null");
        Customers oldCustomers = service.getById(id);
        if (oldCustomers != null) {
            updateCustomers.setId(oldCustomers.getId());
            service.updateCustomers(updateCustomers);
            return updateCustomers;
        } else { // throws notFound if the book to update does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find customer with id: " + id);
        }
    }

    @DeleteMapping("customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomersById(@PathVariable Long id) {
        if (id == null) // throw badRequest if book id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Id cannot be null");
        return service.deleteCustomersById(id) ? "Customer with id " + id + " was deleted successfully" : "Customer with id" + id + " was not deleted";
    }

}