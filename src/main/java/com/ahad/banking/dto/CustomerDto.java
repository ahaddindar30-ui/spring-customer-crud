package com.ahad.banking.dto;

import com.ahad.banking.entity.CustomerType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RealCustomerDto.class, name = "REAL"),
        @JsonSubTypes.Type(value = LegalCustomerDto.class, name = "LEGAL")
})
public abstract class CustomerDto {

    private Integer id;

    @NotBlank(message = "Name can not be empty.")
    @Size(min = 3, max = 15, message = "The name must be between 3 and 15 characters.")
    private String name;

    @NotNull(message = "Entering the age is mandatory.")
    @Min(value = 18, message = "Age must be at least 18.")
    @Max(value = 100, message = "Age must be at most 100.")
    private Integer age;

    @NotBlank(message = "Contact number is required.")
    @Pattern(
            regexp = "^(09\\d{9}|00\\d{12}|\\+\\d{12})$",
            message = "Invalid phone number format."
    )
    private String phone;

    @NotNull(message = "Customer type is required.")
    private CustomerType type;

    @NotBlank(message = "Email can not be empty.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "address can not be empty.")
    private String address;
}