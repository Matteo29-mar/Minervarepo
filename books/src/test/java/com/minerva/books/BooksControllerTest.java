package com.minerva.books;

import com.minerva.books.entities.Book;
import com.minerva.books.controllers.BooksController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

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

        given(controller.getBook(book_1.getId())).willReturn(ResponseEntity.ok(book_1));
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
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).given(controller).getBook(26L);
        mvc.perform(get("/api/v1/books/26")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void getBookByISBN_success_borrowing() throws Exception {

        given(controller.getBookByISBN(book_1.getISBN(), "borrowing")).willReturn(ResponseEntity.ok(book_1.getId()));
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                .contentType(MediaType.APPLICATION_JSON)
                .param("code", book_1.getISBN())
                .param("source", "borrowing"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(equalTo(book_1.getId()), Long.class));

    }

    @Test
    void getBookByISBN_success_borrowing_butNotFound() throws Exception {

        given(controller.getBookByISBN(book_1.getISBN(), "borrowing")).willReturn(ResponseEntity.notFound().build());
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("code", book_1.getISBN())
                        .param("source", "borrowing"))
                .andExpect(status().isNotFound());

    }

    @Test
    void getBookByISBN_success_reservation() throws Exception {

        given(controller.getBookByISBN(book_1.getISBN(), "reservation")).willReturn(ResponseEntity.ok(true));
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("code", book_1.getISBN())
                        .param("source", "reservation"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

    }
    @Test
    void getBookByISBN_success_reservation_butNotFound() throws Exception {

        given(controller.getBookByISBN(book_1.getISBN(), "reservation")).willReturn(ResponseEntity.notFound().build());
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("code", book_1.getISBN())
                        .param("source", "reservation"))
                .andExpect(status().isNotFound());

    }

    @Test
    void getBookByISBN_error_codeIsNull_borrowing() throws Exception {
        given(controller.getBookByISBN(null, "borrowing")).willReturn(null);
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getBookByISBN_error_codeIsNull_reservation() throws Exception {
        given(controller.getBookByISBN(null, "reservation")).willReturn(null);
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


   @Test
   void getBookByISBN_error_bookNotFound_borrowing() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).given(controller).getBookByISBN("9788869059384", "borrowing");
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                .param("code","9788869059384")
                .param("source", "borrowing")).andExpect(status().isNotFound());
    }
    @Test
   void getBookByISBN_error_bookNotFound_reservation() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).given(controller).getBookByISBN("9788869059384", "reservation");
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                .param("code","9788869059384")
                .param("source", "reservation")).andExpect(status().isNotFound());
    }
   @Test
   void getBookByISBN_error_wrongSource() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST)).given(controller).getBookByISBN("9788869059384", "reserve");
        mvc.perform(get(new URI("/api/v1/books/isbn"))
                .param("code","9788869059384")
                .param("source", "reserve")).andExpect(status().isBadRequest());
    }

    @Test
    void addBook_success() throws Exception{
        given(controller.addBook(book_1)).willReturn(ResponseEntity.created(new URI("/" + book_1.getId())).body(book_1));
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

    @Test
    void addBook_error_bookWasNotAdded() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while " + "adding a new book")).given(controller).addBook(book_1);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/books"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(book_1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void updateBook_success() throws Exception{
        book_1_updated.setId(book_1.getId());
        given(controller.updateBook(book_1.getId(), book_1_updated)).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/v1/books/" + book_1.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(book_1_updated_json))
                .andExpect(status().isNoContent());

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
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find book with id: " + 15L)).given(controller).updateBook(15L, book_1_updated);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/books/15" )
                .accept(MediaType.APPLICATION_JSON)
                .content(book_1_updated_json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void deleteBookById_success() throws Exception {
        given(controller.deleteBookById(book_1.getId())).willReturn(ResponseEntity.noContent().build());
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