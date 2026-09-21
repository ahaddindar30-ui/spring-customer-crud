package com.ahad.banking.service.impl;

import com.ahad.banking.dto.CustomerDto;
import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;
import com.ahad.banking.exception.AgeNotAllowedException;
import com.ahad.banking.exception.CustomerDuplicateException;
import com.ahad.banking.exception.CustomerNotFindException;
import com.ahad.banking.mapper.CustomerMapper;
import com.ahad.banking.repository.CustomerRepository;
import com.ahad.banking.repository.RealCustomerRepository;
import com.ahad.banking.service.CustomerService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final RealCustomerRepository realRepository;

    private final CustomerMapper mapper;


    @Override
    public Customer addCustomer(Customer customer) {
        if (customer.getAge() < 18) {
            throw new AgeNotAllowedException("Customer age is less than 18");
        }
        if (repository.existsByEmail(customer.getEmail())) {
            throw new CustomerDuplicateException("Customer already exists");
        }
        return repository.save(customer);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFindException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getActiveCustomers() {
        return repository.findCustomerByDeleted(false);
    }

    @Override
    public Customer updateCustomer(Integer id, CustomerDto newData) {

        Customer customer = repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFindException("Customer not found"));

        if (!customer.getEmail().equalsIgnoreCase(newData.getEmail())) {
            if (repository.existsByEmail(newData.getEmail())) {
                throw new CustomerDuplicateException("Email already exists");
            }
        }

        mapper.mapToCustomer(newData, customer);

        return repository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFindException("Customer not found"));
        customer.setDeleted(true);
        repository.save(customer);
    }

    @Override
    public List<Customer> getDeletedCustomers() {
        return repository.findCustomerByDeleted(true);
    }

    @Override
    public List<Customer> getCustomerByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<RealCustomer> getCustomerByFamily(String family) {
        return realRepository.findByFamily(family);
    }


}
