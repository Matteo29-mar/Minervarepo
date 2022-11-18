package com.minerva.books.Exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException (Long id) {
        super("Could not find book with id: " + id) ;
    }

    public BookNotFoundException( String ISBN) {
        super("Could not find any book with ISBN: " + ISBN);
    }

}
