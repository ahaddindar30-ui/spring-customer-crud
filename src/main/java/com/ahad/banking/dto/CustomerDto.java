package com.ahad.banking.dto;


import com.ahad.banking.entity.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class CustomerDto {
    private Integer id;
    @NotBlank(message = "Name can not be empty.")
    @Size(min = 3, max = 10, message = "The name must be between 3 and 10 characters.")
    private String name;
    @NotBlank(message = "Contact number is required.")
    @Pattern(regexp = "^(09\\d{9}|00\\d{12}|\\+\\d{12})$", message = "Invalid phone number format.")
    private String phone;
    private final CustomerType type;
    @NotBlank(message = "Email can not be empty.")
    @Email(message = "Invalid email format.")
    private String email;
    @NotBlank(message = "address can not be empty.")
    private String address;


}
