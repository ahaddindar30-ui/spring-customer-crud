package com.ahad.banking.controller;

import com.ahad.banking.dto.CustomerDto;


import com.ahad.banking.dto.RealCustomerDto;
import com.ahad.banking.facade.CustomerFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerFacade customerFacade;



    @PostMapping("/add")
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customer) {
        CustomerDto saved = customerFacade.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);


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
    public CustomerDto updateCustomer(@Valid @RequestBody CustomerDto customer, @PathVariable Integer id) {
        return customerFacade.updateCustomer(id, customer);
    }

    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerFacade.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/deleted")
    public List<CustomerDto> getDeletedCustomers() {
        return customerFacade.getDeletedCustomers();
    }

    @GetMapping("/all/name")
    public List<CustomerDto> getCustomerByName(@RequestParam String name) {
        return customerFacade.getCustomerByName(name);
    }

    @GetMapping("/all/family")
    public List<RealCustomerDto> getCustomerByFamily(@RequestParam String family) {
        return customerFacade.getCustomerByFamily(family);
    }
}
