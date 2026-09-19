package com.ahad.banking.facade;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.RealCustomer;
import com.ahad.banking.facade.impl.CustomerFacadeImpl;
import com.ahad.banking.mapper.CustomerMapper;
import com.ahad.banking.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerFacadeImplTest {

    @Mock
    private CustomerService service;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerFacadeImpl facade;

    private RealCustomerDto sampleDto() {
        RealCustomerDto dto = new RealCustomerDto();
        dto.setId(1);
        dto.setName("Ahad");
        dto.setAge(25);
        dto.setEmail("test@gmail.com");
        dto.setPhone("09123456789");
        dto.setAddress("Tehran");
        dto.setFamily("Dev");
        dto.setNationalCode("1234567890");
        return dto;
    }

    private RealCustomer sampleEntity() {
        RealCustomer c = new RealCustomer();
        c.setId(1);
        c.setName("Ahad");
        c.setAge(25);
        c.setEmail("test@gmail.com");
        c.setPhone("09123456789");
        c.setAddress("Tehran");
        c.setFamily("Dev");
        c.setNationalCode("1234567890");
        return c;
    }

    @Test
    void testAddCustomer() {
        Mockito.when(mapper.mapToCustomer(any())).thenReturn(sampleEntity());

        facade.addCustomer(sampleDto());

        Mockito.verify(mapper).mapToCustomer(any());
        Mockito.verify(service).addCustomer(any());
    }

    @Test
    void testGetCustomerById() {
        Mockito.when(service.getCustomerById(1)).thenReturn(sampleEntity());
        Mockito.when(mapper.mapToCustomerDto(any())).thenReturn(sampleDto());

        CustomerDto result = facade.getCustomerById(1);

        assertEquals("Ahad", result.getName());
        Mockito.verify(service).getCustomerById(1);
        Mockito.verify(mapper).mapToCustomerDto(any());
    }

    @Test
    void testGetActiveCustomers() {
        Mockito.when(service.getActiveCustomers()).thenReturn(List.of(sampleEntity()));
        Mockito.when(mapper.mapCustomerDtoList(any())).thenReturn(List.of(sampleDto()));

        List<CustomerDto> result = facade.getActiveCustomers();

        assertEquals(1, result.size());
        Mockito.verify(service).getActiveCustomers();
        Mockito.verify(mapper).mapCustomerDtoList(any());
    }

    @Test
    void testUpdateCustomer() {
        Mockito.when(service.updateCustomer(eq(1), any())).thenReturn(sampleEntity());
        Mockito.when(mapper.mapToCustomerDto(any())).thenReturn(sampleDto());

        CustomerDto result = facade.updateCustomer(1, sampleDto());

        assertEquals("Ahad", result.getName());
        Mockito.verify(service).updateCustomer(eq(1), any());
        Mockito.verify(mapper).mapToCustomerDto(any());
    }

    @Test
    void testDeleteCustomer() {
        facade.deleteCustomer(1);

        Mockito.verify(service).deleteCustomer(1);
    }

    @Test
    void testGetDeletedCustomers() {
        Mockito.when(service.getDeletedCustomers()).thenReturn(List.of(sampleEntity()));
        Mockito.when(mapper.mapCustomerDtoList(any())).thenReturn(List.of(sampleDto()));

        List<CustomerDto> result = facade.getDeletedCustomers();

        assertEquals(1, result.size());
        Mockito.verify(service).getDeletedCustomers();
        Mockito.verify(mapper).mapCustomerDtoList(any());
    }

    @Test
    void testGetCustomerByName() {
        Mockito.when(service.getCustomerByName("Ahad")).thenReturn(List.of(sampleEntity()));
        Mockito.when(mapper.mapCustomerDtoList(any())).thenReturn(List.of(sampleDto()));

        List<CustomerDto> result = facade.getCustomerByName("Ahad");

        assertEquals(1, result.size());
        Mockito.verify(service).getCustomerByName("Ahad");
        Mockito.verify(mapper).mapCustomerDtoList(any());
    }

    @Test
    void testGetCustomerByFamily() {
        Mockito.when(service.getCustomerByFamily("Dev")).thenReturn(List.of(sampleEntity()));
        Mockito.when(mapper.mapRealCustomerDtoList(any())).thenReturn(List.of(sampleDto()));

        List<RealCustomerDto> result = facade.getCustomerByFamily("Dev");

        assertEquals(1, result.size());
        Mockito.verify(service).getCustomerByFamily("Dev");
        Mockito.verify(mapper).mapRealCustomerDtoList(any());
    }
}

