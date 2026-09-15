package com.ahad.banking.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@JsonPropertyOrder({"family","nationalCode"})
@Entity
@Table(name = "real_customer")

@Getter
@Setter
@ToString(callSuper = true)
public class RealCustomer extends Customer implements Serializable {
    private String family;
    private String nationalCode;
    public RealCustomer(String name, String number, String email,String address) {
        super(name, number, email,address ,  CustomerType.REAL);
    }

    public RealCustomer() {
        super(CustomerType.REAL);
    }


}
