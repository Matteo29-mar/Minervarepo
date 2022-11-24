package com.minerva.books.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Could not find book with id: " + id);
    }

    public BookNotFoundException(String ISBN) {
        super("Could not find any book with ISBN: " + ISBN);
    }

}
