package com.minerva.books.controllers;

import com.minerva.books.entities.Book;
import com.minerva.books.services.BookNotificationSender;
import com.minerva.books.services.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class BooksController {

    private Logger bookLogger ;

    private final BooksService service;

    /*
    * idFound is declared in class instead of in method
    * due to the use of a lambda function
    * which would require a final variable
    * rendering idFound useless
    */
    private Long idFound = 0L;


    private final BookNotificationSender sender;

    public BooksController(BooksService service, BookNotificationSender sender, Logger bookLogger) {
        this.service = service;
        this.sender = sender;
        this.bookLogger = LoggerFactory.getLogger(BooksController.class);
    }

    @GetMapping("/books")
    public List<Book> getAll() {
        bookLogger.trace("successfully requested full books list");
        return service.getAllBooks();
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        if (id == null) // throw badRequest if book isbn is null
        {
            bookLogger.error("requested details for null id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        }
        Book found = service.getById(id);
        if (found == null) // throw notFound if requested book doesn't exist
        {
            bookLogger.error("server error on requested details for id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + id);
        }
        bookLogger.trace("successfully requested details for book id {}", id);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("books/isbn")
    public ResponseEntity<Object> getBookByISBN(@Valid @RequestParam String code, @RequestParam String source) {
        if (code == null) // throw badRequest if book isbn is null
        {
            bookLogger.error("requested id for null isbn");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        }
        List<Book> books = service.getByISBN(code);
        if ( books == null) // throw notFound if request isbn is not present in db
        {
            bookLogger.error("server error on requested id for null isbn");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with ISBN: " + code);
        }
        bookLogger.trace("successfully requested id for book isbn {}", code);
        return switch (source) {
            case "borrowing" -> getISBNID(books, code);
            case "reservation" -> getISBNBoolean(books);
            default -> new ResponseEntity<>("Selected source does not exist", HttpStatus.BAD_REQUEST);
        };
    }

    @PostMapping("books")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book newBook) {
        if (newBook == null) // throw badRequest if body doesn't contain a Book Obj
        {
            bookLogger.error("requested book creation with empty or null body");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        }
        Book book = service.addBook(newBook);
        if (book == null) // throws internalError if book was not added
        {
            bookLogger.error("server error on requested book creation");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested book");
        }
        bookLogger.trace("successfully requested book creation");
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (id == null)  // throw badRequest if book id is null
        {
            bookLogger.error("requested edit for book with null id");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        }
        Book oldBook = service.getById(id);
        if (oldBook != null) {
            if(updatedBook.getISBN() != null) oldBook.setISBN(updatedBook.getISBN());
            if(updatedBook.getTitolo() != null) oldBook.setTitolo(updatedBook.getTitolo());
            if(updatedBook.getAnno() >= 1) oldBook.setAnno(updatedBook.getAnno());
            if(updatedBook.getAutore() != null) oldBook.setAutore(updatedBook.getAutore());
            if(updatedBook.getData_inizio() != null) oldBook.setData_inizio(updatedBook.getData_inizio());
            if(updatedBook.getEditore() != null) oldBook.setEditore(updatedBook.getEditore());
            if(updatedBook.getGenere() != null) oldBook.setGenere(updatedBook.getGenere());
            if(updatedBook.getStato() != null) oldBook.setStato(updatedBook.getStato());
            if(updatedBook.getN_pagine() != 0) oldBook.setN_pagine(updatedBook.getN_pagine());
            service.updateBook(oldBook);
            bookLogger.trace("successfully requested edit for book with id {}", id);
            return new ResponseEntity<>(updatedBook, HttpStatus.NO_CONTENT);
        } else { // throws notFound if the book to update does not exist
            bookLogger.error("server error on requested id {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + id);
        }
    }

    @DeleteMapping("books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        if (id == null) // throw badRequest if book id is null
        {
            bookLogger.error("requested delete for null book id {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book ID cannot be null");
        }
        bookLogger.trace("successfully requested delete for book id {}", id);
        return new ResponseEntity<>(
                service.deleteBookById(id) ? "Book with id " + id + " was deleted successfully" : "Book with id" + id + " was not deleted",
                HttpStatus.NO_CONTENT
        );
    }

    private ResponseEntity<Object> getISBNID(List<Book> books, String code){
        idFound = 0L;
        books.forEach((book) -> { // check whole list to update status of books with requested isbn
            // checks if a month has passed since the previous borrowing of the book and the state isn't "libero"
            if (LocalDate.now().isAfter(book.getData_inizio().plusMonths(1)) && this.idFound == 0L) {
                this.idFound = book.getId(); // sets idFound with book id
                book.setData_inizio(LocalDate.now()); // sets borrowing start date to current DateTime
                book.setStato("borrowed"); // updates book status to "borrowed"
                service.updateBook(book); // updates book with new detail
            } else if (LocalDate.now().isAfter(book.getData_inizio().plusMonths(1)) && idFound != 0L ) { // if first available book has already been found
                book.setStato("available"); // updates book status to "libero"
                service.updateBook(book); // updates book in db
                sender.SendNotification("A book with isbn: "+ code + " is now free");
            }
        });
        if (idFound == 0L) {
            sender.SendNotification("We're sorry, there are no available books with isbn: " + code);
            bookLogger.info("successfully requested id for book isbn {} but no available book was found", code);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no available books with ISBN: " + code);
        }
        bookLogger.trace("successfully requested id for book isbn {}", code);
        return new ResponseEntity<>(idFound, HttpStatus.OK); // returns first available book's id to endpoint response
    }

    private ResponseEntity<Object> getISBNBoolean(List<Book> books) {

        for (Book book : books) {
            if (LocalDate.now().isAfter(book.getData_inizio().plusMonths(1))) {
                bookLogger.info("successfully requested id for book isbn and found available book");
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        }
        bookLogger.trace("successfully requested id for book isbn but no available book was found");
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
    }

}
