package com.bridgelabz.bookstoreuserservice.service;

import com.bridgelabz.bookstoreuserservice.dto.UserDTO;
import com.bridgelabz.bookstoreuserservice.exception.UserException;
import com.bridgelabz.bookstoreuserservice.model.UserModel;
import com.bridgelabz.bookstoreuserservice.repository.UserRepository;
import com.bridgelabz.bookstoreuserservice.util.Response;
import com.bridgelabz.bookstoreuserservice.util.ResponseClass;
import com.bridgelabz.bookstoreuserservice.util.ResponseUtil;
import com.bridgelabz.bookstoreuserservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since : 09-09-2022
 * Purpose: Creating method to send Email
 */

@Service
public class UserService implements IUserService {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;

    /**
     * @author  Ashwini Rathod
     * @param   userDTO
     * purpose: Creating method to add user
     */

    @Override
    public ResponseUtil addUser(UserDTO userDTO) {
        UserModel userModel = new UserModel(userDTO);
        userModel.setRegisterDate(LocalDate.now());
        userRepository.save(userModel);
        String body = "User is added successfully with userId " + userModel.getId();
        String subject = "User is added successfully";
        mailService.send(userModel.getEmailId(), body, subject);
        return new ResponseUtil(200, "Successfully", userModel);
    }

    /**
     * @author  Ashwini Rathod
     * @param   userDTO,token,id
     * purpose: Creating method to update user
     */

    @Override
    public ResponseUtil updateUser(UserDTO userDTO, Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                isUserPresent.get().setFirstName(userDTO.getFirstName());
                isUserPresent.get().setLastName(userDTO.getLastName());
                isUserPresent.get().setEmailId(userDTO.getEmailId());
                isUserPresent.get().setPassword(userDTO.getPassword());
                isUserPresent.get().setDob(userDTO.getDob());
                isUserPresent.get().setKyc(userDTO.getKyc());
                isUserPresent.get().setOtp(userDTO.getOtp());
                userRepository.save(isUserPresent.get());
                String body = "User is updated successfully with userId" + isUserPresent.get().getId();
                String subject = "User is updated successfully";
                mailService.send(isUserPresent.get().getEmailId(), body, subject);
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found");
            }
        }
        throw new UserException(400, "Token is Invalid");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,id
     * purpose: Creating method to get user
     */

    @Override
    public ResponseUtil getUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isIdPresent = userRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found");
            }
        }
        throw new UserException(400, "Token is invalid");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token
     * purpose: Creating method to get all users
     */

    @Override
    public List<UserModel> getAllUsers(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isIdPresent = userRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            List<UserModel> isUserPresent = userRepository.findAll();
            if (isUserPresent.size() > 0) {
                return isUserPresent;
            } else {
                throw new UserException(400, "User not found");
            }
        }
        throw new UserException(400, "Token is invalid");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,id
     * purpose: Creating method to delete user
     */

    @Override
    public ResponseUtil deleteUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isIdPresent = userRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new ResponseUtil(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not present");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    /**
     * @author  Ashwini Rathod
     * @param   emailId,password
     * purpose: Creating method to login
     */

    @Override
    public Response login(String emailId, String password) {
        Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            if (isEmailPresent.get().getPassword().equals(password)) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new Response(200, "Login successfully", token);
            }
            throw new UserException(400, "Password is wrong");
        }
        throw new UserException(400, "No user is present with this email id");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token,password
     * purpose: Creating method to update password
     */

    @Override
    public Response updatePassword(String token, String password) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            isId.get().setPassword(password);
            userRepository.save(isId.get());
            return new Response(200, "Successfully", isId.get());
        } else {
            throw new UserException(400, "Token is invalid");
        }
    }

    /**
     * @author  Ashwini Rathod
     * @param   emailId
     * purpose: Creating method to reset password
     */

    @Override
    public Response resetPassword(String emailId) {
        Optional<UserModel> isEmailPresent = userRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String body = "Reset the password using this link \n " + url +
                    "\n This token is use to reset the password \n" + token;
            String subject = "Reset password successfully";
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            return new Response(200, "Successfully", isEmailPresent.get());
        } else {
            throw new UserException(400, "Wrong email id");
        }

    }

    /**
     * @author  Ashwini Rathod
     * @param   token
     * purpose: Creating method to validate token
     */

    @Override
    public Boolean validateToken(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @author  Ashwini Rathod
     * @param   token,id
     * purpose: Creating method to delete user permanently
     */

    @Override
    public Response deletePermanently(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isId = userRepository.findById(userId);
        if (isId.isPresent()) {
            Optional<UserModel> isUserPresent = userRepository.findById(id);
            if (isUserPresent.isPresent()) {
                userRepository.delete(isUserPresent.get());
                return new Response(200, "Successfully", isUserPresent.get());
            } else {
                throw new UserException(400, "User not found with this id");
            }
        } else {
            throw new UserException(400, "Token is wrong");
        }

    }

    /**
     * @author  Ashwini Rathod
     * @param   email
     * purpose: Creating method to validate email
     */

    @Override
    public ResponseClass verifyEmail(String email) {
        Long userId = tokenUtil.decodeToken(email);
        Optional<UserModel> isIdPresent = userRepository.findById(userId);
        if (isIdPresent.isPresent()) {
            return new ResponseClass(200, "User not found", isIdPresent.get());
        } else {
            throw new UserException(400, "User not found");
        }
    }

    /**
     * @author  Ashwini Rathod
     * @param   token
     * purpose: Creating method to verify
     */

    @Override
    public Response verify(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent != null) {
            int sendOtp = (int) ((Math.random() * 900000) + 1000000);
            isUserPresent.get().setOtp(sendOtp);
            userRepository.save(isUserPresent.get());
            return new Response(200, "Successfully" , isUserPresent.get());
        }
        throw new UserException(400, "Invalid token!");
    }

    /**
     * @author  Ashwini Rathod
     * @param   token
     * purpose: Creating method to verify otp
     */

    @Override
    public Response verifyOtp(String token, Long otp) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            if (isUserPresent.get().getOtp() == otp) {
                isUserPresent.get().setVerify(true);
                userRepository.save(isUserPresent.get());
                return new Response(200,"OTP is Verified", isUserPresent.get());
            }
            else
            {
                throw new UserException(400,"OTP is incorrect");
            }
        }
        else
        {
            throw new UserException(400,"User is already Register, Please Try with another Email Id");
        }
    }

    @Override
    public Response purchaseDate(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserModel> isUserPresent = userRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            LocalDate today = LocalDate.now();
            isUserPresent.get().setPurchaseDate(LocalDate.now());
            isUserPresent.get().setExpiryDate(today.plusYears(1));
            String body = "Dear,"+isUserPresent.get().getFirstName()+" You have Purchase the Book for 1 Year Subscription";
            mailService.send(isUserPresent.get().getEmailId(), "Get a 1 Year Subscription for Book", body);
            userRepository.save(isUserPresent.get());
            return new Response(200, "User Purchased Book is Successfully","ExpiryDate : " +isUserPresent.get().getExpiryDate());
        }
        else
        {
            throw new UserException(400,"User is already Register, Please Try with another Email Id");
        }
    }
}
