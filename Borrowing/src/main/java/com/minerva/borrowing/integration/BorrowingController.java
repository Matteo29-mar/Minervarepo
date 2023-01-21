package com.minerva.borrowing.integration;

import com.minerva.borrowing.entities.Borrowing;
import com.minerva.borrowing.service.BorrowingNotificationSender;
import com.minerva.borrowing.service.BorrowingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api")
public class BorrowingController {

	private Logger borrowingLogger;
	@Autowired
	private final BorrowingService service;

	private  final BorrowingNotificationSender sender;

	//Logger logger = Logger.getLogger(BorrowingController.class.getName());

	public BorrowingController(BorrowingService service, BorrowingNotificationSender sender, Logger borrowingLogger) {

		this.service = service;
		this.sender = sender;
		this.borrowingLogger = LoggerFactory.getLogger(BorrowingController.class);
	}

	
	@GetMapping("/borrowing")
	public List<Borrowing>getAll(){

		borrowingLogger.trace("Get all borrowings");
		return this.service.getAllBorrowing();
	}
	@GetMapping("borrowing/{id}")
	public ResponseEntity<Borrowing> getBorrowing(@PathVariable String id) {
		if (id == null) // throw badRequest if borrowing id is null
		{
			borrowingLogger.error("requested details for null id");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
		}
		Borrowing found = service.getBorrowingById(id);
        if (found == null) // throw notFound if requested borrowing doesn't exist
			 {
				 borrowingLogger.error("server error on requested details for id {}", id);
				 throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + id);
			 }
		borrowingLogger.trace("successfully requested details for book id {}", id);
        return new ResponseEntity<>(found, HttpStatus.OK);

	}
	
	@PostMapping("borrowing")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Borrowing> addBorrowing(@Valid @RequestBody Borrowing newBorrowing) {
        if (newBorrowing == null) // throw badRequest if body doesn't contain a borrowing Obj
		{
			borrowingLogger.error("requested borrowing creation with empty or null body");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
		}
		Borrowing borrowing = service.addBorrowing(newBorrowing);
        if (borrowing == null) // throws internalError if borrowing was not added
		{
			borrowingLogger.error("server error on requested borrowing creation");
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested borrowing");
		}
		borrowingLogger.trace("successfully requested borrowing creation");
        return new ResponseEntity<>(borrowing, HttpStatus.CREATED);
    }
	
	
	 @PutMapping("borrowing/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public ResponseEntity<Borrowing> updateBorrowing(@PathVariable String id, @RequestBody Borrowing updatedBorrowing) {
	        if (id == null)  // throw badRequest if borrowing id is null
			{
				borrowingLogger.error("requested edit for borrowing with null id");
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
			}
	        Borrowing oldBorrowing = service.getBorrowingById(id);
	        if (oldBorrowing != null) {
	            if(updatedBorrowing.getId_libri() != null) oldBorrowing.setId_libri(updatedBorrowing.getId_libri());
	            if(updatedBorrowing.getData_inizio() != null) oldBorrowing.setData_inizio(updatedBorrowing.getData_inizio());
	            if(updatedBorrowing.getData_scadenza() != null) oldBorrowing.setData_scadenza(updatedBorrowing.getData_scadenza());
	            if(updatedBorrowing.getData_riconsegna() != null) oldBorrowing.setData_riconsegna(updatedBorrowing.getData_riconsegna());
	            if(updatedBorrowing.getId_cliente() != null) oldBorrowing.setId_cliente(updatedBorrowing.getId_cliente());
	            service.updateBorrowing(oldBorrowing);
				borrowingLogger.trace("successfully requested edit for borrowing with id {}", id);
	            return new ResponseEntity<>(updatedBorrowing, HttpStatus.NO_CONTENT);
	        } else { // throws notFound if the book to update does not exist
	            borrowingLogger.error("server error on requested id {}", id);
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + id);
	        }
	    }
	 
	 @DeleteMapping("borrowing/{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	    public ResponseEntity<String> deleteBorrowingById(@PathVariable String id) {
	        if (id == null) // throw badRequest if borrowing id is null
			{
				borrowingLogger.error("requested delete for null borrowing id {}", id);
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id libri cannot be null");
			}
			borrowingLogger.trace("successfully requested delete for borrowing id {}", id);
			return new ResponseEntity<>(service.deleteBorrowingById(id) ? "Borrowing with id " + id + " was deleted successfully" : "Borrowing with id" + id + " was not deleted", HttpStatus.NO_CONTENT);

	 }
}
