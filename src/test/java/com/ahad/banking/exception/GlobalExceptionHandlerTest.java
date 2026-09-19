package com.ahad.banking.exception;

import com.ahad.banking.controller.CustomerController;
import com.ahad.banking.facade.CustomerFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerFacade facade;

    @Test
    void testCustomerNotFoundException() throws Exception {
        Mockito.when(facade.getCustomerById(99))
                .thenThrow(new CustomerNotFindException("Customer not found"));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void testDuplicateException() throws Exception {
        Mockito.when(facade.getCustomerById(1))
                .thenThrow(new CustomerDuplicateException("Duplicate email"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isConflict())   // 409
                .andExpect(content().string("Duplicate email"));
    }

    @Test
    void testAgeNotAllowedException() throws Exception {
        Mockito.when(facade.getCustomerById(1))
                .thenThrow(new AgeNotAllowedException("Age must be >= 18"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Age must be >= 18"));
    }

    @Test
    void testGenericException() throws Exception {
        Mockito.when(facade.getCustomerById(1))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }
}
