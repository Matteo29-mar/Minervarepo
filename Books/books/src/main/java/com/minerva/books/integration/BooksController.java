package com.minerva.books.integration;

import com.minerva.books.entities.Book;
import com.minerva.books.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class BooksController {

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
        return service.getById(id);
    }

    @PostMapping("books")
    public Book addBook(@RequestBody Book newBook){
        return service.addBook(newBook);
    }

    @PutMapping("books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook){

        Book oldBook = service.getById(id);
        if(oldBook != null){
            updatedBook.setId(oldBook.getId());
            service.updateBook(updatedBook);
        }
        return null;
    }

}
