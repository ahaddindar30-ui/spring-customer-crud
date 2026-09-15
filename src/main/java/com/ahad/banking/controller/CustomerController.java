package com.ahad.banking.controller;

import com.ahad.banking.dto.CustomerDto;


import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.facade.CustomerFacade;
import com.ahad.banking.facade.impl.CustomerFacadeImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CustomerController {

    private final CustomerFacade customerFacade;

  public CustomerController(CustomerFacadeImpl customerFacade){
      this.customerFacade=customerFacade;
  }

    @PostMapping("/add")
    public void addCustomer(@RequestBody CustomerDto customer) {
        customerFacade.addCustomer(customer);
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Integer id) {
        return customerFacade.getCustomerById(id);
    }

    @GetMapping("/all")
    public List<CustomerDto> getAllCustomers() {
        return customerFacade.getActiveCustomers();
    }

    @PutMapping("/update/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customer, @PathVariable Integer id) {
        return customerFacade.updateCustomer(id, customer);
    }

    @DeleteMapping("/deleted/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerFacade.deleteCustomer(id);
    }

    @GetMapping("/all/deleted")
    public List<CustomerDto> getDeletedCustomers() {
        return customerFacade.getDeletedCustomers();
    }

    @GetMapping("/all/name")
    public List<CustomerDto> getCustomerByName(@RequestBody String name) {
        return customerFacade.getCustomerByName(name);
    }

    @GetMapping("/all/family")
    public List<RealCustomerDto> getCustomerByFamily(@RequestBody String family) {
        return customerFacade.getCustomerByFamily(family);
    }
}
