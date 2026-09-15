package com.ahad.banking.dto;

import com.ahad.banking.entity.CustomerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class RealCustomerDto extends CustomerDto{
    @NotBlank(message = "family can not be empty.")
    @Size(min = 3, max = 10, message = "The family must be between 3 and 10 characters.")
    private String family;
    @NotBlank(message = "nationalCode can not be empty.")
    @Pattern(regexp = "^\\d{10}$",message ="The national code must be 10 digits." )
    private String nationalCode;

    public RealCustomerDto(Integer id, String name, String phone, String email, String address) {
        super(id, name, phone, CustomerType.REAL, email, address);
    }

    public RealCustomerDto() {
            super(CustomerType.REAL);
    }
}
