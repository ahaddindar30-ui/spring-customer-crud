package com.ahad.banking.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@JsonPropertyOrder({"id", "deleted", "name", "phone", "email", "address", "type"})
@Entity
@Table(name = "customer")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalCustomer.class, name = "LEGAL"),
        @JsonSubTypes.Type(value = RealCustomer.class, name = "REAL")

})
@Getter
@Setter
@ToString
public abstract class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_seq", allocationSize = 1)
    private Integer id;

    private Integer age;

    private String name;
    private String phone;
    @Enumerated(EnumType.STRING)
    private CustomerType type;
    private String email;
    private String address;
    private boolean deleted;

    @Version
    private long version;

    public Customer(String name, Integer age, String phone, String email, String address, CustomerType type) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.type = type;
        this.email = email;
        this.address = address;
        this.deleted = false;
    }

    public Customer(CustomerType type) {
        this.deleted = false;
        this.type = type;
    }

    protected Customer() {

    }



}
