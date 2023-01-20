package com.minerva.borrowing.integration;

import java.util.List;
import java.util.logging.Logger;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.minerva.borrowing.entities.Borrowing;
import com.minerva.borrowing.service.BorrowingService;

//import javax.validation.Valid;
@RestController
@RequestMapping("api")
public class BorrowingController {

	private Logger borrowingLogger;
	private final BorrowingService service;

	Logger logger = Logger.getLogger(BorrowingController.class.getName());

	public BorrowingController(BorrowingService service) {

		this.service = service;
	}
	
	@GetMapping("/borrowing")
	public List<Borrowing>getAll(){

		logger.info("Get all borrowings");
		return this.service.getAllBorrowing();
	}
	@GetMapping("borrowing/{id}")
	public ResponseEntity<Borrowing> getBorrowing(@PathVariable String id) {
		if (id == null) // throw badRequest if borrowing id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        Borrowing found = service.getBorrowingById(id);
        if (found == null) // throw notFound if requested borrowing doesn't exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + id);
        return new ResponseEntity<>(found, HttpStatus.OK);

	}
	
	@PostMapping("borrowing")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Borrowing> addBorrowing(@RequestBody Borrowing newBorrowing) {
        if (newBorrowing == null) // throw badRequest if body doesn't contain a borrowing Obj
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
        Borrowing borrowing = service.addBorrowing(newBorrowing);
        if (borrowing == null) // throws internalError if borrowing was not added
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested borrowing");
        return new ResponseEntity<>(borrowing, HttpStatus.CREATED);
    }
	
	
	 @PutMapping("borrowing/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public ResponseEntity<Borrowing> updateBorrowing(@PathVariable String id, @RequestBody Borrowing updatedBorrowing) {
	        if (id == null)  // throw badRequest if borrowing id is null
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
	        Borrowing oldBorrowing = service.getBorrowingById(id);
	        if (oldBorrowing != null) {
	            if(updatedBorrowing.getId_libri() != null) oldBorrowing.setId_libri(updatedBorrowing.getId_libri());
	            if(updatedBorrowing.getData_inizio() != null) oldBorrowing.setData_inizio(updatedBorrowing.getData_inizio());
	            if(updatedBorrowing.getData_scadenza() != null) oldBorrowing.setData_scadenza(updatedBorrowing.getData_scadenza());
	            if(updatedBorrowing.getData_riconsegna() != null) oldBorrowing.setData_riconsegna(updatedBorrowing.getData_riconsegna());
	            if(updatedBorrowing.getId_cliente() != null) oldBorrowing.setId_cliente(updatedBorrowing.getId_cliente());
	            service.updateBorrowing(oldBorrowing);
	            return new ResponseEntity<>(updatedBorrowing, HttpStatus.NO_CONTENT);
	        } else { // throws notFound if the book to update does not exist
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + id);
	        }
	    }
	 
	 @DeleteMapping("borrowing/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	    public ResponseEntity<String> deleteBorrowingById(@PathVariable String id) {
	        if (id == null) // throw badRequest if borrowing id is null
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
			return new ResponseEntity<>(
	                service.deleteBorrowingById(id) ? "Borrowing with id " + id + " was deleted successfully" : "Borrowing with id" + id + " was not deleted",
	                HttpStatus.NO_CONTENT);

	 }
}
