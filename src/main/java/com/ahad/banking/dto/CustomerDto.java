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
    @Pattern(regexp = "^0\\\\d{10}$|^00\\\\d{12}$|\\\\+\\\\d{12}$",
            message = "The phone number must start with 09 and 00 and be 11 digits long.")
    private String phone;
    private final CustomerType type;
    @NotBlank(message = "email can not be empty.")
    @Email(message = "The email format entered is not correct.")
    @Pattern(regexp ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",message = "Invalid email format")
    private String email;
    @NotBlank(message = "address can not be empty.")
    private String address;



}
