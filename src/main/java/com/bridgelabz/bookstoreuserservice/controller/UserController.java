package com.bridgelabz.bookstoreuserservice.controller;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.service.IUserService;
import com.bridgelabz.bookstoreuserservice.util.Response;
import com.bridgelabz.bookstoreuserservice.util.ResponseClass;
import com.bridgelabz.bookstoreuserservice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;


    @GetMapping("/welcome")
    public String welcomeMessage() {
        return "Welcome to bookstore project";
    }

    @PostMapping("/addUser")
    public ResponseEntity<ResponseUtil> addUser(@Valid @RequestBody UserDTO userDTO) {
        ResponseUtil responseUtil = userService.addUser(userDTO);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ResponseUtil> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id,
                                                   @RequestHeader String token) {
        ResponseUtil responseUtil = userService.updateUser(userDTO, id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<ResponseUtil> getUser(@RequestParam Long id, @RequestHeader String token) {
        ResponseUtil responseUtil = userService.getUser(id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List> getAllUsers(@RequestHeader String token) {
        List<UserModel> responseUtil = userService.getAllUsers(token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<ResponseUtil> deleteUser(@RequestHeader String token, @RequestParam Long id) {
        ResponseUtil responseUtil = userService.deleteUser(id, token);
        return new ResponseEntity<>(responseUtil, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestParam String emailId, @RequestParam String password) {
        Response response = userService.login(emailId, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<Response> updatePassword(@RequestHeader String token, @RequestParam String password) {
        Response response = userService.updatePassword(token, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/validate/{token}")
    public Boolean validateToken(@PathVariable String token) {
        return userService.validateToken(token);
    }

    @GetMapping("/validate/{email}")
    public ResponseEntity<ResponseClass> validateEmail(@PathVariable String email) {
        ResponseClass responseClass = userService.verifyEmail(email);
        return new ResponseEntity<>(responseClass, HttpStatus.OK);
    }

    @DeleteMapping("/deletePermanently")
    public ResponseEntity<Response> deletePermanently(@RequestParam Long id, @RequestHeader String token) {
        Response response = userService.deletePermanently(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/verifyOtp/{token}")
    public ResponseEntity verifyOtp(@PathVariable String token, @RequestParam Long otp) {
        Response response = userService.verifyOtp(token, otp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/purchasesubscription/{token}")
    public ResponseEntity<Response> purchaseDate(@PathVariable String token) {
        Response response =userService.purchaseDate(token);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
