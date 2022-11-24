package com.minerva.books;

import com.minerva.books.entities.Book;
import com.minerva.books.integration.BooksController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BooksController controller;


        Book book_1 = new Book(1L, "9788869059384", "TestTitle", "TestAuthor", 2000, "TestGenre", 200, "in_prestito", "TestEditor", LocalDate.now());
        String book_1_json = "{\"id\":1,\"titolo\":\"TestTitle\",\"autore\":\"TestAuthor\",\"anno\":2022,\"genere\":\"TestGenre\",\"n_pagine\":400,\"stato\":\"in_prestito\",\"editore\":\"TestPublisher\",\"data_inizio\":\"2022-10-22\",\"isbn\":\"9788869059384\"}";
        String book_1_updated_json = "{\"id\":1,\"titolo\":\"TestTitleUpdated\",\"autore\":\"TestAuthor\",\"anno\":2022,\"genere\":\"TestGenre\",\"n_pagine\":400,\"stato\":\"in_prestito\",\"editore\":\"TestPublisher\",\"data_inizio\":\"2022-10-22\",\"isbn\":\"9788869059384\"}";
        Book book_1_updated = new Book(1L, "9788869059384", "TestTitleUpdated", "TestAuthor", 2000, "TestGenre", 200, "in_prestito", "TestEditor", LocalDate.now());
        Book book_2 = new Book(2L, "9788869059384", "TestTitle", "TestAuthor", 2000, "TestGenre", 200, "in_prestito", "TestEditor", LocalDate.now().minusMonths(2));
        Book book_3 = new Book(3L, "9788869059384", "TestTitle", "TestAuthor", 2000, "TestGenre", 200, "libero", "TestEditor", LocalDate.now());
        Book book_4 = new Book(4L, "9788869059385", "TestTitle", "TestAuthor", 2000, "TestGenre", 200, "libero", "TestEditor", LocalDate.now().minusMonths(2));

    @Test
    void getAll_success() throws  Exception{

        List<Book> allBooks = new ArrayList<>(Arrays.asList(book_1, book_2, book_3, book_4));
        given(controller.getAll()).willReturn(allBooks);
        mvc.perform(get(new URI("/api/v1/books"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].titolo", is(book_1.getTitolo())));
    }

    @Test
    void getBookById_success() throws Exception{

        given(controller.getBook(book_1.getId())).willReturn(book_1);
        mvc.perform(get(new URI("/api/v1/books/"+ book_1.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("titolo", is(book_1.getTitolo())));

    }

    @Test
    void getBookById_error_idIsNull() throws Exception{
        given(controller.getBook(null)).willReturn(null);
        mvc.perform(get(new URI("/api/v1/books/"+ null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookById_error_bookNotFound() throws Exception{
        mvc.perform(get("/api/v1/books/26")).andExpect(status().isNotFound());
    }

    @Test
    void getBookByISBN_success() throws Exception {

        given(controller.getBookByISBN(book_1.getISBN())).willReturn(book_1.getId());
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("code", book_1.getISBN()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(equalTo(book_1.getId()), Long.class));

    }

    @Test
    void getBookByISBN_error_codeIsNull() throws Exception {
        given(controller.getBookByISBN(null)).willReturn(null);
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBookByISBN_error_bookNotFound() throws Exception{
        given(controller.getBookByISBN(book_4.getISBN()+10L)).willReturn(null);
        mvc.perform(get(new URI("/api/v1/books/isbn"))).andExpect(status().isBadRequest());
    }

    @Test
    void addBook_success() throws Exception{
        given(controller.addBook(book_1)).willReturn(book_1);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/books"))
                .accept(MediaType.APPLICATION_JSON)
                .content(book_1_json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("titolo", is(book_1.getTitolo())));
    }@Test
    void addBook_error_newBookIsNull() throws Exception{
        given(controller.addBook(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/books"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    /*
    @Test
    void addBook_error_bookWasNotAdded() throws Exception{
        given(controller.addBook(book_1)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/books"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(book_1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", is("Something went wrong while " + "adding a new book"  )));
    }
    */

    @Test
    void updateBook_success() throws Exception{
        book_1_updated.setId(book_1.getId());
        given(controller.updateBook(book_1.getId(), book_1_updated)).willReturn(book_1_updated);
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/v1/books/" + book_1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(book_1_updated_json))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("titolo", equalTo("TestTitleUpdated")));

    }

    @Test
    void updateBook_error_idIsNull() throws Exception{
        book_1_updated.setId(book_1.getId());
        given(controller.updateBook(null, book_1_updated)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/v1/books/" + null))
                .contentType(MediaType.APPLICATION_JSON)
                .content(book_1_updated_json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBook_error_bookNotFound() throws Exception{
        book_1_updated.setId(book_1.getId());
        given(controller.updateBook(15L, book_1_updated)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/books/15" )
                .accept(MediaType.APPLICATION_JSON)
                .content(book_1_updated_json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void deleteBookById_success() throws Exception {
        given(controller.deleteBookById(book_1.getId())).willReturn("Book with id " + book_1.getId() + " was deleted successfully");
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/v1/books/" + book_1.getId())))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBookById_error_idIsNull() throws Exception {
        given(controller.deleteBookById(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/v1/books/" + null)))
                .andExpect(status().isBadRequest());
    }

}