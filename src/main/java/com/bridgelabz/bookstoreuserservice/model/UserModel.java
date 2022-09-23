package com.bridgelabz.bookstoreuserservice.model;


import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 19-09-2022
 * Purpose : Model for the User data Registration
 */

@Data
@Entity
@Table(name = "UserDetails")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column
    private String kyc;
    @Column(name = "emailId")
    private String emailId;
    @Column
    private String password;
    @Column
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    @Column(name = "registerDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate registerDate;
    @Column(name = "updatedDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate updatedDate;
    @Column
    private long otp;

    private boolean verify;
    @Column(name = "purchaseDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;
    @Column(name = "expiryDate")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expiryDate;

    public UserModel(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.kyc = userDTO.getKyc();
        this.emailId = userDTO.getEmailId();
        this.password = userDTO.getPassword();
        this.dob = userDTO.getDob();

    }

    public UserModel() {
    }

}

