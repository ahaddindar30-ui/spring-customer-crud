package com.ahad.banking.repository;

import com.ahad.banking.entity.Customer;
import com.ahad.banking.entity.RealCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List <Customer> findByName(String name);
    List <RealCustomer> findByFamily(String family);

    Customer getCustomerById(Integer id);

    List<Customer> getCustomerByDeleted(boolean delete);

    boolean existsByEmail(String email);
}
