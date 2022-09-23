package com.bridgelabz.bookstoreuserservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 19-09-2022
 * Purpose : dto for the User data
 */

@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First letter of first name must be capital")
    @NotNull(message = "First name can not be NULL")
    public String firstName;

    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First letter of last name must be capital")
    @NotNull(message = "Last name can not be NULL")
    public String lastName;

    public String kyc;

    @Email(message = "Insert valid email Id")
    @Pattern(regexp = "[a-z][A-Z a-z 0-9]+[@][a-z]+[.][a-z]{2,}", message = "Invalid email id")
    public String emailId;

    @NotNull(message = "Enter Your Password")
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}", message = "Invalid password")
    public String password;

    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate dob;
    public long otp;
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate purchaseDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate expiryDate;
}
