package com.ahad.banking.facade;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.RealCustomerDto;


import java.util.List;

public interface CustomerFacade {

    void addCustomer(CustomerDto customerDto);

    CustomerDto getCustomerById(Integer id);

    List<CustomerDto> getActiveCustomers();

    CustomerDto updateCustomer(Integer id, CustomerDto newData);

    void deleteCustomer(Integer id);

    List<CustomerDto> getDeletedCustomers();

    List<CustomerDto> getCustomerByName(String name);
    List<RealCustomerDto> getCustomerByFamily(String family);

}
