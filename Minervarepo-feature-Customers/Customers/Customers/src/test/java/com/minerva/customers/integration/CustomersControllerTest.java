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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import org.springframework.http.HttpStatus;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.core.Is.is;
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

    String customer_1_updated_json = "{\"customersid\":1,\"nome\":\"mario\",\"cognome\":\"luigi\",\"email\":\"mariolu@gmail.com\",\"telefono\":\"9876543210\",\"data_nascita\":\"05-06-1987\"}";
    Customers customer_1_updated = new Customers(1L, "Mario", "Luigi", "mariolu@gmail.com", "9876543210", "05-06-1987" );
    @Test
    void addCustomers()  throws Exception{
        given(controller.addCustomers(customer_1)).willReturn(ResponseEntity.created(new URI("/" + customer_1.getCustomersid())).body(customer_1));
        mvc.perform(MockMvcRequestBuilders.post(new URI("/api/v1/customer"))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(customer1_json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void getCustomers() throws Exception{

        given(controller.getCustomers(customer_1.getCustomersid())).willReturn(ResponseEntity.ok(customer_1));
        mvc.perform(MockMvcRequestBuilders.get(new URI("/api/v1/" +  customer_1.getCustomersid()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome", is(customer_1.getNome())));
    }

    @Test
    void getAllCustomers() throws Exception{
        List<Customers> allCustomers = new ArrayList<>(Arrays.asList(customer_1));
        given(controller.getAllCustomers()).willReturn(allCustomers);
        mvc.perform(MockMvcRequestBuilders.get(new URI("/api/v1"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is(customer_1.getNome())));

    }
    @Test
    void updateCustomer()  throws Exception{
        customer_1_updated.setCustomersid(customer_1.getCustomersid());
        given(controller.updateCustomer(customer_1.getCustomersid(),customer_1_updated)).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.put(new URI("/api/v1/" + customer_1.getCustomersid()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(customer_1_updated_json))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllCustomers() throws Exception{
        given(controller.deleteAllCustomers()).willReturn(ResponseEntity.noContent().build());
        mvc.perform(MockMvcRequestBuilders.delete(new URI("/api/v1" )))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCustomers() {

    }
}
