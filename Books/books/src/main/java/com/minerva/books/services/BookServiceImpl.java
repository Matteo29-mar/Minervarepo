package com.minerva.books.services;

import com.minerva.books.Exceptions.BookNotFoundException;
import com.minerva.books.Exceptions.InternalErrorException;
import com.minerva.books.entities.Book;
import com.minerva.books.repo.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BooksService{

    private List<Book> empty = new ArrayList<Book>();
    private final BooksRepo repo;

    @Autowired
    public BookServiceImpl(BooksRepo repo) {
        this.repo = repo;
    }

    @Override
    public Book getById(Long id) {
        if (id != null ){
            return repo.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        }else {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {

        List<Book> books = repo.findAll();

        if (books.isEmpty()){
            return empty;
         }
        return books;
    }

    @Override
    public List<Book> getByISBN(String ISBN) {

        if (ISBN != null) {
            List<Book> books = repo.findBooksByISBN(ISBN);
            if (!books.isEmpty()){
                return books;
            }else {
                throw new BookNotFoundException(ISBN);
            }
        }
        return null;
    }

    @Override
    public Book addBook(Book newBook) {

        if (newBook != null){
            return repo.save(newBook);
        }
        return null;
    }

    @Override
    public Book updateBook(Book updatedBook) {
        if(updatedBook != null){
            return repo.save(updatedBook);
        }
        return null;
    }

    @Override
    public boolean deleteBookById(Long id) {

        if (id != null){
            repo.deleteById(id);
            return repo.findById(id).isEmpty();
        }else {
            throw new InternalErrorException("deleting a book");
        }
    }
}
