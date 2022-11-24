package com.minerva.books.services;

import com.minerva.books.entities.Book;
import com.minerva.books.repo.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BooksService {

    private final List<Book> empty = new ArrayList<>();
    private final BooksRepo repo;

    @Autowired
    public BookServiceImpl(BooksRepo repo) {
        this.repo = repo;
    }

    @Override
    public Book getById(Long id) {

        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + id));

    }

    @Override
    public List<Book> getAllBooks() {

        List<Book> books = repo.findAll();

        if (books.isEmpty()) {
            return empty;
        }
        return books;
    }

    @Override
    public List<Book> getByISBN(String ISBN) {

        List<Book> books = repo.findBooksByISBN(ISBN);
        System.out.println(books);
        if (!books.isEmpty()) {
            return books;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with ISBN: " + ISBN);
        }
    }

    @Override
    public Book addBook(Book newBook) {
        return repo.save(newBook);
    }


    @Override
    public Book updateBook(Book updatedBook) {

        return repo.save(updatedBook);

    }

    @Override
    public boolean deleteBookById(Long id) {

        repo.deleteById(id);
        return repo.findById(id).isEmpty();

    }
}
