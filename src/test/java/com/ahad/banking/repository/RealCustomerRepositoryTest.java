package com.ahad.banking.repository;

import com.ahad.banking.entity.RealCustomer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RealCustomerRepositoryTest {

    @Autowired
    private RealCustomerRepository repository;

    private RealCustomer sample() {
        RealCustomer c = new RealCustomer();
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
    void testFindByFamily() {
        repository.save(sample());

        List<RealCustomer> list = repository.findByFamily("Dev");

        assertEquals(1, list.size());
        assertEquals("Dev", list.get(0).getFamily());
    }


}

