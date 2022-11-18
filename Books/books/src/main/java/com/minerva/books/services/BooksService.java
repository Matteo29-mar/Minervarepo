package com.minerva.books.services;

import com.minerva.books.entities.Book;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BooksService {

    //GET
    Book getById(Long id);
    List<Book> getAllBooks();
    List<Book> getByISBN(String ISBN);
    //POST
    Book addBook(Book newBook);
    //PUT
    Book updateBook(Book updatedBook);
    //DELETE
    boolean deleteBookById(Long id);

}
