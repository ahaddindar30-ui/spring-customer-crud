package com.ahad.banking.mapper;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.LegalCustomerDto;
import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.LegalCustomer;
import com.ahad.banking.entity.RealCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    List<CustomerDto> mapCustomerDtoList(List<Customer> customerList);

    List<RealCustomerDto> mapRealCustomerDtoList(List<RealCustomer> realCustomerList);


    default CustomerDto mapToCustomerDto(Customer customer) {

        if (customer instanceof LegalCustomer legalCustomer) {
            return mapToLegalCustomerDto(legalCustomer);
        }

        if (customer instanceof RealCustomer realCustomer) {
            return mapToRealCustomerDto(realCustomer);
        }

        throw new IllegalArgumentException(
                "Unsupported customer type: " + customer.getClass().getSimpleName()
        );
    }


    RealCustomerDto mapToRealCustomerDto(RealCustomer realCustomer);

    LegalCustomerDto mapToLegalCustomerDto(LegalCustomer legalCustomer);


    default Customer mapToCustomer(CustomerDto customerDto, Customer customer) {

        if (customerDto instanceof LegalCustomerDto legalDto
                && customer instanceof LegalCustomer legalCustomer) {

            return mapToLegalCustomer(legalDto, legalCustomer);
        }

        if (customerDto instanceof RealCustomerDto realDto
                && customer instanceof RealCustomer realCustomer) {

            return mapToRealCustomer(realDto, realCustomer);
        }

        throw new IllegalArgumentException(
                "Customer DTO type does not match customer entity type"
        );
    }


    default Customer mapToCustomer(CustomerDto customerDto) {

        if (customerDto instanceof LegalCustomerDto legalDto) {

            return mapToLegalCustomer(
                    legalDto,
                    new LegalCustomer()
            );
        }

        if (customerDto instanceof RealCustomerDto realDto) {

            return mapToRealCustomer(
                    realDto,
                    new RealCustomer()
            );
        }

        throw new IllegalArgumentException(
                "Unsupported customer DTO type: "
                        + customerDto.getClass().getSimpleName()
        );
    }


    @Mapping(target = "id", ignore = true)
    RealCustomer mapToRealCustomer(
            RealCustomerDto realCustomerDto,
            @MappingTarget RealCustomer realCustomer
    );


    @Mapping(target = "id", ignore = true)
    LegalCustomer mapToLegalCustomer(
            LegalCustomerDto legalCustomerDto,
            @MappingTarget LegalCustomer legalCustomer
    );
}