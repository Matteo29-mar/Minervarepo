package com.minerva.books.integration;

import com.minerva.books.Exceptions.BookNotFoundException;
import com.minerva.books.Exceptions.InternalErrorException;
import com.minerva.books.entities.Book;
import com.minerva.books.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class BooksController {

    private Long idFound = 0L;

    @Autowired
    private final BooksService service;
    public BooksController(BooksService service) {
        this.service = service;
    }

    @GetMapping("books")
    public List<Book> getAll() {
        return service.getAllBooks();
    }

    @GetMapping("books/{id}")
    public Book getBook(@PathVariable Long id){
        Book found = service.getById(id);
        if( found == null )
            throw new BookNotFoundException(id);
        return found;
    }

    @GetMapping("books/isbn")
    public Long getBookByISBN(@RequestParam String code){
        List<Book> books = service.getByISBN(code);
        books.stream().forEach((book) -> {
            if( LocalDate.now().isAfter(book.getData_inizio().plusMonths(1)) && book.getStato() != "libero") {
                if(this.idFound == 0L){
                    this.idFound = book.getId();
                    book.setData_inizio(LocalDate.now());
                    service.updateBook(book);
                }else {
                    book.setStato("libero");
                    service.updateBook(book);
                }
            } else if (LocalDate.now().isAfter(book.getData_inizio().minusMonths(1)) && book.getStato() == "libero" && idFound != 0L){
                idFound = book.getId();
                book.setStato("prenotato");
                book.setData_inizio(LocalDate.now());
                service.updateBook(book);
            }
        });
        return idFound;
    }

    @PostMapping("books")
    public Book addBook(@RequestBody Book newBook){
        Book book = service.addBook(newBook);
        if (book == null)
            throw new InternalErrorException("adding a book");
        return book;
    }

    @PutMapping("books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook){

        Book oldBook = service.getById(id);
        if(oldBook != null){
            updatedBook.setId(oldBook.getId());
            service.updateBook(updatedBook);
            return updatedBook;
        }else {
            throw new InternalErrorException("updating a book");
        }
    }

    @DeleteMapping("books/{id}")
    public String deleteBookById(@PathVariable Long id){
        return service.deleteBookById(id) ? "Books with id " + id + " was deleted successfully" : "Book with id" + id + " was not deleted";
    }

}
