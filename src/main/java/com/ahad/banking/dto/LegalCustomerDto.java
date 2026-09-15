package com.ahad.banking.dto;

import com.ahad.banking.entity.CustomerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class LegalCustomerDto extends CustomerDto{
    @NotBlank(message = "Contact fax number is required.")
    @Pattern(regexp = "^(09\\d{9}|00\\d{12}|\\+\\d{12})$", message = "Invalid fax number format.")
    private String faxNumber;
    @NotBlank(message = "Contact company registration is required.")
    @Pattern(regexp ="^\\d{4,20}$",message = "The company registration number must be between 4 and 20.")
    private String companyRegistration;
    @NotBlank(message ="Customer companyName is empty or null." )
    private String companyName;

    public LegalCustomerDto(Integer id, String name, String phone, String email, String address) {
        super(id, name, phone, CustomerType.LEGAL, email, address);

    }

    public LegalCustomerDto() {
        super(CustomerType.LEGAL);
    }
}


