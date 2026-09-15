package com.ahad.banking.facade.impl;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.facade.CustomerFacade;
import com.ahad.banking.mapper.CustomerMapper;
import com.ahad.banking.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerFacadeImpl implements CustomerFacade {
    private final CustomerService customerService;
    private final CustomerMapper mapper;
    public CustomerFacadeImpl(CustomerService customerService, CustomerMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }
    @Override
    public void addCustomer(CustomerDto customerDto) {
         customerService.addCustomer(mapper.mapToCustomer(customerDto));
    }

    @Override
    public CustomerDto getCustomerById(Integer id) {
        return mapper.mapToCustomerDto(
                customerService.getCustomerById(id));
    }

    @Override
    public List<CustomerDto> getActiveCustomers() {
        return mapper.mapCustomerDtoList(customerService.getActiveCustomers());
    }

    @Override
    public CustomerDto updateCustomer(Integer id, CustomerDto newData) {
        return mapper.mapToCustomerDto(customerService.updateCustomer(id,newData));
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerService.deleteCustomer(id);
    }

    @Override
    public List<CustomerDto> getDeletedCustomers() {
        return mapper.mapCustomerDtoList(
                customerService.getDeletedCustomers());
    }

    @Override
    public List<CustomerDto> getCustomerByName(String name) {
        return mapper.mapCustomerDtoList(customerService.getCustomerByName(name));
    }

    @Override
    public List<RealCustomerDto> getCustomerByFamily(String family) {
        return mapper.mapRealCustomerDtoList(customerService.getCustomerByFamily(family));
    }


}
