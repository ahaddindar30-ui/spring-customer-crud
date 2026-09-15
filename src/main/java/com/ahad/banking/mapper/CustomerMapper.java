package com.ahad.banking.mapper;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.LegalCustomerDto;
import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.LegalCustomer;
import com.ahad.banking.entity.RealCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {


    List<CustomerDto> mapCustomerDtoList(List<Customer> customerList);
    List<RealCustomerDto> mapRealCustomerDtoList(List<RealCustomer> realcustomerList);

    default CustomerDto mapToCustomerDto(Customer customer) {
        if (customer instanceof LegalCustomer) {
            return mapToLegalCustomerDto((LegalCustomer) customer);
        } else {
            return mapToRealCustomerDto((RealCustomer) customer);
        }
    }

    RealCustomerDto mapToRealCustomerDto(RealCustomer realCustomer);


    LegalCustomerDto mapToLegalCustomerDto(LegalCustomer legalCustomer);


    default Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        if (customerDto instanceof LegalCustomerDto) {
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    (LegalCustomer) customer);
        } else {
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    (RealCustomer) customer);
        }
    }

    default Customer mapToCustomer(CustomerDto customerDto) {
        if (customerDto instanceof LegalCustomerDto) {
            return mapToLegalCustomer((LegalCustomerDto) customerDto,
                    new LegalCustomer(null, null, null,null));
        } else {
            return mapToRealCustomer((RealCustomerDto) customerDto,
                    new RealCustomer(null, null, null, null));
        }
    }

    @Mapping(target = "id", ignore = true)
    RealCustomer mapToRealCustomer(RealCustomerDto realCustomerDto,
                                   @MappingTarget RealCustomer realCustomer);

    @Mapping(target = "id", ignore = true)
    LegalCustomer mapToLegalCustomer(LegalCustomerDto legalCustomerDto,
                                     @MappingTarget LegalCustomer legalCustomer);

}
