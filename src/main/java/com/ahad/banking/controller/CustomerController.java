package com.ahad.banking.controller;

import com.ahad.banking.entity.Customer;
import com.ahad.banking.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CustomerController {

    private final TestService testService;

    public CustomerController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/add")
    public Customer addCustomer(@RequestBody Customer customer){
       return testService.addCustomer(customer);
    }
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        return testService.getCustomerById(id);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers(){
        return testService.getActiveCustomers();
    }

    @PutMapping("/update/{id}")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable Integer id){
        return testService.updateCustomer(id, customer);
    }

    @DeleteMapping("/deleted/{id}")
    public void deleteCustomer(@PathVariable Integer id){
        testService.deleteCustomer(id);
    }




}
