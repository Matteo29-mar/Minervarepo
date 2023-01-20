package com.minerva.borrowing;


import org.junit.jupiter.api.Test;
import com.minerva.borrowing.entities.Borrowing;
import com.minerva.borrowing.integration.BorrowingController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;




import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;








import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;




import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;








@RunWith(SpringRunner.class)
@WebMvcTest(BorrowingApplicationTests.class)
class BorrowingApplicationTests {




    @Autowired
    private MockMvc mvc;




    @MockBean
    private BorrowingController controller;






    Borrowing borrowing_1 = new Borrowing("1B", new String[]{"1L,2L"}, LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "1C");
    String borrowing_1_json = "{\"id\":\"1B\",\"id_libri\":[\"1L,2L\"],\"data_inizio\":\"2023-01-11\",\"data_scadenza\":\"2023-02-10\",\"data_riconsegna\":\"2023-02-08\",\"id_cliente\":\"1C\"}";
    String borrowing_1_updated_json = "{\"id\":\"1B\",\"id_libri\":[\"1L,2L\"],\"data_inizio\":\"2023-01-11\",\"data_scadenza\":\"2023-02-10\",\"data_riconsegna\":\"2023-02-08\",\"id_cliente\":\"1C\"}";
    Borrowing borrowing_1_updated = new Borrowing("1B_updated", new String[]{"1L,2L"}, LocalDate.now(), LocalDate.now().plusDays(30),  LocalDate.now() ,"1C");
    Borrowing borrowing_2 = new Borrowing("2B", new String[]{"2L"},LocalDate.now() ,LocalDate.now().plusDays(30) , LocalDate.now(), "2C");
    Borrowing borrowing_3 = new Borrowing("3B", new String[]{"3L"}, LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "3C");
    Borrowing borrowing_4 = new Borrowing("4B", new String[]{"4L"}, LocalDate.now(), LocalDate.now().plusDays(30), LocalDate.now(), "4C");




    @Test
    void getAll_success() throws  Exception{




        List<Borrowing> allBorrowings = new ArrayList<>(Arrays.asList(borrowing_1));
        given(controller.getAll()).willReturn(allBorrowings);
        mvc.perform(get(new URI("/api/borrowing"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(borrowing_1.getId())))
        ;
    }








    @Test
    void getBorrowingById_success() throws Exception{


        given(controller.getBorrowing(borrowing_1.getId())).willReturn(ResponseEntity.ok(borrowing_1));
        mvc.perform(get(new URI("/api/borrowing/"+ borrowing_1.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        /*.andExpect(jsonPath("Id", is(borrowing_1.getId())))*/;








    }








    @Test
    void getBorrowingById_error_idIsNull() throws Exception{
        given(controller.getBorrowing(null)).willReturn(null);
        mvc.perform(get(new URI("/api/borrowing/"+ null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }








    @Test
    void getBorrowingById_error_borrowingNotFound() throws Exception{
        willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).given(controller).getBorrowing("50B");
        mvc.perform(get("/api/borrowing/50B")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }




    @Test
    void addBorrowing_success() throws Exception{
        given(controller.addBorrowing(borrowing_1)).willReturn(ResponseEntity.created(new URI("/" + borrowing_1.getId())).body(borrowing_1));
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id_libri", is(borrowing_1.getId_libri())));


    }
    @Test
    void addBorrowing_error_newBorrowingIsNull() throws Exception{
        given(controller.addBorrowing(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }








 /* @Test
  void addBorrowing_error_borrowingWasNotAdded() throws Exception{
      willThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while " + "adding a new borrowing")).given(controller).addBorrowing(borrowing_1);
      mvc.perform(MockMvcRequestBuilders.post(new URI("/api/borrowing"))
                      .accept(MediaType.APPLICATION_JSON)
                      .content(borrowing_1_json)
                      .contentType(MediaType.APPLICATION_JSON))
                      .andExpect(status().isInternalServerError());
  }*/
















    @Test
    void updateBorrowing_success() throws Exception{
        borrowing_1_updated.setId(borrowing_1.getId());
        given(controller.updateBorrowing(borrowing_1.getId(), borrowing_1_updated)).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/borrowing/" + borrowing_1.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(borrowing_1_updated_json))
                .andExpect(status().isNoContent());








    }








/*  @Test
  void updateBorrowing_error_idIsNull() throws Exception{
      borrowing_1_updated.setId(borrowing_1.getId());
      given(controller.updateBorrowing(null, borrowing_1_updated)).willReturn(null);
      mvc.perform(MockMvcRequestBuilders.put(new URI("/api/borrowing/" + null))
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(borrowing_1_updated_json))
              .andExpect(status().isBadRequest());
  }








  @Test
  void updateBorrowing_error_borrowingNotFound() throws Exception{
      borrowing_1_updated.setId(borrowing_1.getId());
      willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find borrowing with id: " + "55B")).given(controller).updateBorrowing("55b", borrowing_1_updated);
      mvc.perform(MockMvcRequestBuilders.put("/api/borrowing/55B" )
              .accept(MediaType.APPLICATION_JSON)
              .content(borrowing_1_updated_json)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isNotFound());
  }*/








    @Test
    void deleteBorrowingById_success() throws Exception {
        given(controller.deleteBorrowingById(borrowing_1.getId())).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/borrowing/" + borrowing_1.getId())))
                .andExpect(status().isNoContent());
    }








 /* @Test
  void deleteBorrowingById_error_idIsNull() throws Exception {
      given(controller.deleteBorrowingById(null)).willReturn(null);
      mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/borrowing/" + null)))
              .andExpect(status().isBadRequest());
  }*/












}



