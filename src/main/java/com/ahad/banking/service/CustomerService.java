package com.ahad.banking.service;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;


import java.util.List;


public interface CustomerService {


    void addCustomer(Customer customer);

    Customer getCustomerById(Integer id);

    List<Customer> getActiveCustomers();

    Customer updateCustomer(Integer id, CustomerDto newData);

    void deleteCustomer(Integer id);

    List<Customer> getDeletedCustomers();

    List<Customer> getCustomerByName(String name);
    List<RealCustomer> getCustomerByFamily(String family);



}
