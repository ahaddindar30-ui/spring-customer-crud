package com.ahad.banking.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@JsonPropertyOrder({"faxNumber","nameCompony","companyRegistration"})

@Entity
@Table(name = "legal_customer")
@Getter
@Setter
@ToString(callSuper = true)
public class LegalCustomer extends Customer implements Serializable {
    private String faxNumber;
    private String companyRegistration;
    private String nameCompony;
    public LegalCustomer(String name, Integer age, String number, String email,String address) {
        super(name,age ,number, email,address ,  CustomerType.LEGAL);
    }

    public LegalCustomer() {
        super(CustomerType.LEGAL);
    }

}
