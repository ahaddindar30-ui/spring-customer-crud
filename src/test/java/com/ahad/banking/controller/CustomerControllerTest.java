package com.ahad.banking.controller;

import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.CustomerType;
import com.ahad.banking.facade.CustomerFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerFacade facade;

    @Autowired
    private ObjectMapper mapper;

    private RealCustomerDto sample() {
        RealCustomerDto dto = new RealCustomerDto();
        dto.setId(1);
        dto.setType(CustomerType.REAL);
        dto.setName("Ahad");
        dto.setAge(25);
        dto.setPhone("09123456789");
        dto.setEmail("test@gmail.com");
        dto.setAddress("Tehran");
        dto.setFamily("Dev");
        dto.setNationalCode("1234567890");
        return dto;
    }

    @Test
    void testAddCustomer() throws Exception {
        RealCustomerDto dto = sample();

        mockMvc.perform(post("/api/customers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        Mockito.verify(facade).addCustomer(any());
    }

    @Test
    void testGetCustomerById() throws Exception {
        Mockito.when(facade.getCustomerById(1)).thenReturn(sample());

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ahad"))
                .andExpect(jsonPath("$.family").value("Dev"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Mockito.when(facade.getActiveCustomers()).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/customers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Mockito.when(facade.updateCustomer(eq(1), any())).thenReturn(sample());

        mockMvc.perform(put("/api/customers/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(sample())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ahad"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/deleted/1"))
                .andExpect(status().isOk());

        Mockito.verify(facade).deleteCustomer(1);
    }

    @Test
    void testGetCustomerByFamily() throws Exception {
        Mockito.when(facade.getCustomerByFamily("Dev")).thenReturn(List.of(sample()));

        mockMvc.perform(get("/api/customers/all/family?family=Dev"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].family").value("Dev"));
    }
}
