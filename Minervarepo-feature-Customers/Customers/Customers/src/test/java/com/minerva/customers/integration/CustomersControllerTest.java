package com.minerva.customers.integration;

import com.minerva.customers.entities.Customers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomersController.class)
class CustomersControllerTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private CustomersController controller;

    Customers customer_1 = new Customers(1L, "Mario", "Luigi", "mariolu@gmail.com", "1234567890", "05-06-1987" );
    String customer1_json ="{\"customersid\":1,\"nome\":\"mario\",\"cognome\":\"luigi\",\"email\":\"mariolu@gmail.com\",\"telefono\":\"1234567890\",\"data_nascita\":\"05-06-1987\"}";
    @Test
    void addCustomers()  throws Exception{
        given(controller.addCustomers(customer_1)).willReturn(ResponseEntity.created(new URI("/" + customer_1.getCustomersid())).body(customer_1));
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/customer"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(customer1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("email", is(customer_1.getEmail())));
    }

    @Test
    void getCustomers() {
    }

    @Test
    void getAllCustomers() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteAllCustomers() {
    }

    @Test
    void deleteCustomers() {
    }
}