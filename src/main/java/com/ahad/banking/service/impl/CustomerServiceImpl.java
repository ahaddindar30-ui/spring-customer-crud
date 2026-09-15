package com.ahad.banking.service.impl;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;
import com.ahad.banking.mapper.CustomerMapper;
import com.ahad.banking.repository.CustomerRepository;
import com.ahad.banking.service.CustomerService;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }



    public void addCustomer(Customer customer) {
         repository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Customer> getActiveCustomers() {
        List<Customer> customerList = repository.getCustomerByDeleted(false);
        if (customerList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        } else {
            return customerList;
        }
    }
    public Customer updateCustomer(Integer id, CustomerDto newData) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }

        if (!customer.getEmail().equalsIgnoreCase(newData.getEmail())) {
            if (repository.existsByEmail(newData.getEmail())) {
                throw new RuntimeException("این ایمیل قبلاً توسط کاربر دیگری ثبت شده است!");
            }
        }
        mapper.mapToCustomer(newData, customer);
        return repository.save(customer);
    }


    public void deleteCustomer(Integer id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("کاربر پیدا نشد!"));

        repository.delete(customer);
    }


    public List<Customer> getDeletedCustomers() {
        List<Customer> customerList = repository.getCustomerByDeleted(true);
        if (customerList.isEmpty()) {
            throw new RuntimeException("Customer not found");
        } else {
            return customerList;
        }
    }

    public List<Customer> getCustomerByName(String name){
        return repository.findByName(name);
    }

    @Override
    public List<RealCustomer> getCustomerByFamily(String family) {
        return repository.findByFamily(family);
    }


}
