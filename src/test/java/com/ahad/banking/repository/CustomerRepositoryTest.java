package com.ahad.banking.repository;

import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private RealCustomer sample() {
        RealCustomer c = new RealCustomer();
        c.setName("Ahad");
        c.setAge(25);
        c.setEmail("test@gmail.com");
        c.setPhone("09123456789");
        c.setAddress("Tehran");
        c.setFamily("Dev");
        c.setNationalCode("1234567890");
        c.setDeleted(false);
        return c;
    }

    @Test
    void testSaveCustomer() {
        Customer saved = repository.save(sample());
        assertNotNull(saved.getId());
    }

    @Test
    void testExistsByEmail() {
        repository.save(sample());
        assertTrue(repository.existsByEmail("test@gmail.com"));
        assertFalse(repository.existsByEmail("unknown@gmail.com"));
    }

    @Test
    void testFindById() {
        Customer saved = repository.save(sample());
        Customer found = repository.findById(saved.getId()).orElse(null);

        assertNotNull(found);
        assertEquals("Ahad", found.getName());
    }

    @Test
    void testGetCustomerByDeleted() {
        RealCustomer c1 = sample();
        RealCustomer c2 = sample();
        c2.setEmail("another@gmail.com");
        c2.setDeleted(true);

        repository.save(c1);
        repository.save(c2);

        List<Customer> active = repository.getCustomerByDeleted(false);
        List<Customer> deleted = repository.getCustomerByDeleted(true);

        assertEquals(1, active.size());
        assertEquals(1, deleted.size());
    }

    @Test
    void testFindByName() {
        repository.save(sample());

        List<Customer> list = repository.findByName("Ahad");

        assertEquals(1, list.size());
        assertEquals("Ahad", list.get(0).getName());
    }
}

