package com.minerva.borrowing;

import com.minerva.borrowing.entities.Borrowing;
import com.minerva.borrowing.integration.BorrowingController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc


@RunWith(SpringRunner.class)
@WebMvcTest(BorrowingApplicationTests.class)

class BorrowingApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BorrowingController controller;


    Borrowing borrowing_1 = new Borrowing("01B", "01L", LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "01C");
    String borrowing_1_json = "{\"id\":01B,\"id_libri\":\"01L\",\"data_inizio\":\"2023-01-11\",\"data_scadenza\":\"2023-02-10\",\"data_riconsegna\":\"2023-02-08\",\"id_cliente\":\"01C\"}";
    String borrowing_1_updated_json = "{\"id\":01B,\"id_libri\":\"01L\",\"data_inizio\":\"2023-01-11\",\"data_scadenza\":\"2023-02-10\",\"data_riconsegna\":\"2023-02-08\",\"id_cliente\":\"01C\"}";
    Borrowing borrowing_1_updated = new Borrowing("01B_updated", "1L", LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "01C");
    Borrowing borrowing_2 = new Borrowing("02B", "02L", LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "02C");
    Borrowing borrowing_3 = new Borrowing("03B", "03L", LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "03C");
    Borrowing borrowing_4 = new Borrowing("04B", "04L", LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "04C");

    @Test
    void getAll_success() throws  Exception{

        List<Borrowing> allBorrowings = new ArrayList<>(Arrays.asList(borrowing_1, borrowing_2, borrowing_3, borrowing_4));
        given(controller.getAll()).willReturn(allBorrowings);
        mvc.perform(get(new URI("/api/borrowings"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id_libri", is(borrowing_1.getId_libri())));
    }

    @Test
    void getBorrowingById_success() throws Exception{

        given(controller.getBorrowing(borrowing_1.getId())).willReturn(ResponseEntity.ok(borrowing_1));
        mvc.perform(get(new URI("/api/borrowing/"+ borrowing_1.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id_libri", is(borrowing_1.getId_libri())));

    }

    @Test
    void getBorrowingById_error_idIsNull() throws Exception{
        given(controller.getBorrowing(null)).willReturn(null);
        mvc.perform(get(new URI("/api/borrowing/"+ null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBorrowingById_error_borrowingNotFound() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).given(controller).getBorrowing("55B");
        mvc.perform(get("/api/borrowing/55B")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    /*@Test
    void getBorrowingByIdLibri_success_borrowing() throws Exception {

       given(controller.getBorrowingByIdLibri(book_1.getISBN(), "borrowing")).willReturn(ResponseEntity.ok(book_1.getId()));
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
*/
    @Test
    void addBorrowing_success() throws Exception{
        given(controller.addBorrowing(borrowing_1)).willReturn(ResponseEntity.created(new URI("/" + borrowing_1.getId())).body(borrowing_1));
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id_libri", is(borrowing_1.getId_libri())));
    }@Test
    void addBorrowing_error_newBorrowingIsNull() throws Exception{
        given(controller.addBorrowing(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addBorrowing_error_borrowingWasNotAdded() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while " + "adding a new borrowing")).given(controller).addBorrowing(borrowing_1);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void updateBorrowing_success() throws Exception{
        borrowing_1_updated.setId(borrowing_1.getId());
        given(controller.updateBorrowing(borrowing_1.getId(), borrowing_1_updated)).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/borrowing/" + borrowing_1.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_updated_json))
                .andExpect(status().isNoContent());

    }

    @Test
    void updateBorrowing_error_idIsNull() throws Exception{
        borrowing_1_updated.setId(borrowing_1.getId());
        given(controller.updateBorrowing(null, borrowing_1_updated)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/v1/borrowing/" + null))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_updated_json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBorrowing_error_borrowingNotFound() throws Exception{
        borrowing_1_updated.setId(borrowing_1.getId());
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + "55B")).given(controller).updateBorrowing("55b", borrowing_1_updated);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/books/55B" )
                .accept(MediaType.APPLICATION_JSON)
                .content(borrowing_1_updated_json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    void deleteBorrowingById_success() throws Exception {
        given(controller.deleteBorrowingById(borrowing_1.getId())).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/borrowing/" + borrowing_1.getId())))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBorrowingById_error_idIsNull() throws Exception {
        given(controller.deleteBorrowingById(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/borrowing/" + null)))
                .andExpect(status().isBadRequest());
    }

}


