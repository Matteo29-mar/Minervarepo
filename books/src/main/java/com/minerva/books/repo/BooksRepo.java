package com.minerva.books.repo;

import com.minerva.books.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepo extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.ISBN like ?1")
    List<Book> findBooksByISBN(@NonNull String ISBN);

}
