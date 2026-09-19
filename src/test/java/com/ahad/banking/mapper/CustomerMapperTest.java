package com.ahad.banking.mapper;


import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.LegalCustomerDto;
import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.LegalCustomer;
import com.ahad.banking.entity.RealCustomer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    private RealCustomer sampleReal() {
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

    private LegalCustomer sampleLegal() {
        LegalCustomer c = new LegalCustomer();
        c.setId(2);
        c.setName("CompanyX");
        c.setAge(0);
        c.setEmail("company@gmail.com");
        c.setPhone("021123456");
        c.setAddress("Tehran");
        c.setCompanyRegistration("998877");
        return c;
    }

    private RealCustomerDto sampleRealDto() {
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

    private LegalCustomerDto sampleLegalDto() {
        LegalCustomerDto dto = new LegalCustomerDto();
        dto.setId(2);
        dto.setName("CompanyX");
        dto.setAge(0);
        dto.setEmail("company@gmail.com");
        dto.setPhone("021123456");
        dto.setAddress("Tehran");
        dto.setCompanyRegistration("998877");
        return dto;
    }

    // -------------------- ENTITY → DTO --------------------

    @Test
    void testMapToRealCustomerDto() {
        RealCustomerDto dto = mapper.mapToRealCustomerDto(sampleReal());

        assertEquals("Ahad", dto.getName());
        assertEquals("Dev", dto.getFamily());
    }

    @Test
    void testMapToLegalCustomerDto() {
        LegalCustomerDto dto = mapper.mapToLegalCustomerDto(sampleLegal());

        assertEquals("CompanyX", dto.getName());
        assertEquals("998877", dto.getCompanyRegistration());
    }

    @Test
    void testMapToCustomerDto_PolymorphicReal() {
        CustomerDto dto = mapper.mapToCustomerDto(sampleReal());

        assertTrue(dto instanceof RealCustomerDto);
        assertEquals("Ahad", dto.getName());
    }

    @Test
    void testMapToCustomerDto_PolymorphicLegal() {
        CustomerDto dto = mapper.mapToCustomerDto(sampleLegal());

        assertTrue(dto instanceof LegalCustomerDto);
        assertEquals("CompanyX", dto.getName());
    }

    @Test
    void testMapToCustomerDto_UnsupportedType() {
        Customer unknown = new Customer() {};

        assertThrows(IllegalArgumentException.class,
                () -> mapper.mapToCustomerDto(unknown));
    }

    // -------------------- DTO → ENTITY --------------------

    @Test
    void testMapToCustomer_CreateReal() {
        Customer entity = mapper.mapToCustomer(sampleRealDto());

        assertTrue(entity instanceof RealCustomer);
        assertEquals("Ahad", entity.getName());
    }

    @Test
    void testMapToCustomer_CreateLegal() {
        Customer entity = mapper.mapToCustomer(sampleLegalDto());

        assertTrue(entity instanceof LegalCustomer);
        assertEquals("CompanyX", entity.getName());
    }

    @Test
    void testMapToCustomer_UpdateReal() {
        RealCustomer entity = sampleReal();
        entity.setId(999); // باید تغییر نکند

        mapper.mapToRealCustomer(sampleRealDto(), entity);

        assertEquals(999, entity.getId()); // id تغییر نمی‌کند
        assertEquals("Ahad", entity.getName());
        assertEquals("Dev", entity.getFamily());
    }

    @Test
    void testMapToCustomer_UpdateLegal() {
        LegalCustomer entity = sampleLegal();
        entity.setId(888);

        mapper.mapToLegalCustomer(sampleLegalDto(), entity);

        assertEquals(888, entity.getId()); // id تغییر نمی‌کند
        assertEquals("CompanyX", entity.getName());
        assertEquals("998877", entity.getCompanyRegistration());
    }

    @Test
    void testMapToCustomer_UpdateWrongType() {
        assertThrows(IllegalArgumentException.class,
                () -> mapper.mapToCustomer(sampleRealDto(), sampleLegal()));
    }

    // -------------------- LIST MAPPING --------------------

    @Test
    void testMapCustomerDtoList() {
        List<CustomerDto> list = mapper.mapCustomerDtoList(List.of(sampleReal(), sampleLegal()));

        assertEquals(2, list.size());
        assertTrue(list.get(0) instanceof RealCustomerDto);
        assertTrue(list.get(1) instanceof LegalCustomerDto);
    }

    @Test
    void testMapRealCustomerDtoList() {
        List<RealCustomerDto> list = mapper.mapRealCustomerDtoList(List.of(sampleReal()));

        assertEquals(1, list.size());
        assertEquals("Ahad", list.get(0).getName());
    }
}

