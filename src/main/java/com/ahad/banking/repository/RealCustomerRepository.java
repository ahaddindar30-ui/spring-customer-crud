package com.ahad.banking.repository;

import com.ahad.banking.entity.RealCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealCustomerRepository extends JpaRepository<RealCustomer,Integer> {
    List<RealCustomer> findByFamily(String family);

}
