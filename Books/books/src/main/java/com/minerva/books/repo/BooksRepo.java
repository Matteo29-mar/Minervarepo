package com.minerva.books.repo;

import com.minerva.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {

    List<Book> findByISBN(String ISBN);

}
