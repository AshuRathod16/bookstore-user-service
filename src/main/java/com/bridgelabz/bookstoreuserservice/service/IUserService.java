package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.util.Response;
import com.bridgelabz.bookstoreuserservice.util.ResponseClass;
import com.bridgelabz.bookstoreuserservice.util.ResponseUtil;

import java.util.List;

public interface IUserService {

    public ResponseUtil addUser(UserDTO userDTO);

    public Response login(String emailId, String password);

    public ResponseUtil updateUser(UserDTO userDTO, Long id, String token);

    public List<UserModel> getAllUsers(String token);

    public ResponseUtil deleteUser(Long id, String token);

    public ResponseUtil getUser(Long id, String token);

    public Response updatePassword(String token, String password);

    public Response resetPassword(String emailId);

//    public Response restoreUser(Long id, String token);
//
//    public Response deleteUsers(Long id, String token);

    public Response deletePermanently(Long id, String token);

    public Boolean validateToken(String token);

//    Response addProfilePic(Long id, MultipartFile profilePic) throws IOException;

    ResponseClass verifyEmail(String Email);

    public Response verify(String token);

    public Response verifyOtp(String token, Long otp);
    public Response purchaseDate(String token);

}

