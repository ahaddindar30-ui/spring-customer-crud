package com.ahad.banking.service;

import com.ahad.banking.entity.Customer;
import com.ahad.banking.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final CustomerRepository repository;

    public TestService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer addCustomer(Customer customer) {
        return repository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Customer> getActiveCustomers() {
        return repository.findAll();
    }

    public Customer updateCustomer(Integer id, Customer newData) {
        Customer customer = repository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        } else {
            customer.setName(newData.getName());
            customer.setEmail(newData.getEmail());
        }
        return repository.save(customer);
    }

    public boolean deleteCustomer(Integer id) {
        if (!repository.existsById(id)) {
            return false;
        } else {
            repository.deleteById(id);
            return true;


        }
    }
}
