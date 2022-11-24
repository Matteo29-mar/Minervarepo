package com.minerva.books.integration;

import com.minerva.books.entities.Book;
import com.minerva.books.services.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1")
public class BooksController {

    private final BooksService service;

    /*
    * idFound is declared in class instead of in method
    * due to the use of a lambda function
    * which would require a final variable
    * rendering idFound useless
    */
    private Long idFound = 0L;

    public BooksController(BooksService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<Book> getAll() {
        return service.getAllBooks();
    }

    @GetMapping("books/{id}")
    public Book getBook(@PathVariable Long id) {
        if (id == null) // throw badRequest if book isbn is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id cannot be null");
        Book found = service.getById(id);
        if (found == null) // throw notFound if requested book doesn't exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + id);
        return found;
    }

    @GetMapping("books/isbn")
    public Long getBookByISBN(@Valid @RequestParam String code) {
        if (code == null) // throw badRequest if book isbn is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        List<Book> books = service.getByISBN(code);
        if ( books == null) // throw notFound if request isbn is not present in db
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with ISBN: " +  code);
        books.forEach((book) -> { // check whole list to update status of books with requested isbn
            // checks if a month has passed since the previous borrowing of the book and the state isn't "libero"
            if (LocalDate.now().isAfter(book.getData_inizio().plusMonths(1)) && !Objects.equals(book.getStato(), "libero")) {
                if (this.idFound == 0L) { // checks if first available book hasn't already been found
                    this.idFound = book.getId(); // sets idFound with book id
                    book.setData_inizio(LocalDate.now()); // sets borrowing start date to current DateTime
                    book.setStato("in_prestito"); // updates book status to "in_prestito"
                    service.updateBook(book); // updates book with new detail
                } else { // if first available book has already been found
                    book.setStato("libero"); // updates book status to "libero"
                    service.updateBook(book); // updates book in db
                }
            // checks if a month has passed since previous borrowing and status == "libero" and first available book hasn't been found yet
            } else if (LocalDate.now().isAfter(book.getData_inizio().plusMonths(1)) && Objects.equals(book.getStato(), "libero") && idFound != 0L) {
                idFound = book.getId(); // sets idFound to book id
                book.setStato("in_prestito"); // sets book status to "in_prestito"
                book.setData_inizio(LocalDate.now()); // sets book's borrowing start date to current DateTime
                service.updateBook(book); // updates book in db
            }
        });
        Long bookId = idFound;
        idFound = 0L; // resets idFound fo future requests
        if (bookId == 0L)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There are no available books with ISBN: " + code);
        return bookId; // returns first available book's id to endpoint response
    }

    @PostMapping("books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@Valid @RequestBody Book newBook) {
        if (newBook == null) // throw badRequest if body doesn't contain a Book Obj
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        Book book = service.addBook(newBook);
        if (book == null) // throws internalError if book was not added
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not create requested book");
        return book;
    }

    @PutMapping("books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        if (id == null)  // throw badRequest if book id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        Book oldBook = service.getById(id);
        if (oldBook != null) {
            updatedBook.setId(oldBook.getId());
            service.updateBook(updatedBook);
            return updatedBook;
        } else { // throws notFound if the book to update does not exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + id);
        }
    }

    @DeleteMapping("books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteBookById(@PathVariable Long id) {
        if (id == null) // throw badRequest if book id is null
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN code cannot be null");
        return service.deleteBookById(id) ? "Book with id " + id + " was deleted successfully" : "Book with id" + id + " was not deleted";
    }

}