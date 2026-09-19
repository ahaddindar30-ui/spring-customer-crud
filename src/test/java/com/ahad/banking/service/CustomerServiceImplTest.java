package com.ahad.banking.service;

import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;
import com.ahad.banking.exception.AgeNotAllowedException;
import com.ahad.banking.exception.CustomerDuplicateException;
import com.ahad.banking.exception.CustomerNotFindException;
import com.ahad.banking.mapper.CustomerMapper;
import com.ahad.banking.repository.CustomerRepository;
import com.ahad.banking.repository.RealCustomerRepository;
import com.ahad.banking.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private RealCustomerRepository realRepository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerServiceImpl service;

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

    // -------------------- ADD CUSTOMER --------------------

    @Test
    void testAddCustomer_AgeNotAllowed() {
        RealCustomer c = sampleEntity();
        c.setAge(17);

        assertThrows(AgeNotAllowedException.class, () -> service.addCustomer(c));
    }

    @Test
    void testAddCustomer_DuplicateEmail() {
        Mockito.when(repository.existsByEmail("test@gmail.com")).thenReturn(true);

        assertThrows(CustomerDuplicateException.class, () -> service.addCustomer(sampleEntity()));
    }

    @Test
    void testAddCustomer_Success() {
        Mockito.when(repository.existsByEmail(any())).thenReturn(false);

        service.addCustomer(sampleEntity());

        Mockito.verify(repository).save(any());
    }

    // -------------------- GET CUSTOMER BY ID --------------------

    @Test
    void testGetCustomerById_NotFound() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFindException.class, () -> service.getCustomerById(1));
    }

    @Test
    void testGetCustomerById_Success() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(sampleEntity()));

        Customer result = service.getCustomerById(1);

        assertEquals("Ahad", result.getName());
    }

    // -------------------- GET ACTIVE CUSTOMERS --------------------

    @Test
    void testGetActiveCustomers_NotFound() {
        Mockito.when(repository.getCustomerByDeleted(false)).thenReturn(List.of());

        assertThrows(CustomerNotFindException.class, () -> service.getActiveCustomers());
    }

    @Test
    void testGetActiveCustomers_Success() {
        Mockito.when(repository.getCustomerByDeleted(false)).thenReturn(List.of(sampleEntity()));

        List<Customer> result = service.getActiveCustomers();

        assertEquals(1, result.size());
    }

    // -------------------- UPDATE CUSTOMER --------------------

    @Test
    void testUpdateCustomer_NotFound() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFindException.class,
                () -> service.updateCustomer(1, sampleDto()));
    }

    @Test
    void testUpdateCustomer_DuplicateEmail() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(sampleEntity()));
        Mockito.when(repository.existsByEmail("new@gmail.com")).thenReturn(true);

        RealCustomerDto dto = sampleDto();
        dto.setEmail("new@gmail.com");

        assertThrows(CustomerDuplicateException.class,
                () -> service.updateCustomer(1, dto));
    }

    @Test
    void testUpdateCustomer_Success() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(sampleEntity()));
        Mockito.when(repository.save(any())).thenReturn(sampleEntity());

        Customer result = service.updateCustomer(1, sampleDto());

        assertEquals("Ahad", result.getName());
        Mockito.verify(mapper).mapToCustomer(any(), any());
        Mockito.verify(repository).save(any());
    }


    // -------------------- DELETE CUSTOMER --------------------

    @Test
    void testDeleteCustomer_NotFound() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFindException.class, () -> service.deleteCustomer(1));
    }

    @Test
    void testDeleteCustomer_Success() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.of(sampleEntity()));

        service.deleteCustomer(1);

        Mockito.verify(repository).save(any());
    }

    // -------------------- GET DELETED CUSTOMERS --------------------

    @Test
    void testGetDeletedCustomers_NotFound() {
        Mockito.when(repository.getCustomerByDeleted(true)).thenReturn(List.of());

        assertThrows(CustomerNotFindException.class, () -> service.getDeletedCustomers());
    }

    @Test
    void testGetDeletedCustomers_Success() {
        Mockito.when(repository.getCustomerByDeleted(true)).thenReturn(List.of(sampleEntity()));

        List<Customer> result = service.getDeletedCustomers();

        assertEquals(1, result.size());
    }

    // -------------------- GET CUSTOMER BY NAME --------------------

    @Test
    void testGetCustomerByName() {
        Mockito.when(repository.findByName("Ahad")).thenReturn(List.of(sampleEntity()));

        List<Customer> result = service.getCustomerByName("Ahad");

        assertEquals(1, result.size());
    }

    // -------------------- GET CUSTOMER BY FAMILY --------------------

    @Test
    void testGetCustomerByFamily() {
        Mockito.when(realRepository.findByFamily("Dev")).thenReturn(List.of(sampleEntity()));

        List<RealCustomer> result = service.getCustomerByFamily("Dev");

        assertEquals(1, result.size());
    }
}

