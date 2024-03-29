package com.geoprom.cl.api.backend.services.Users;


import com.geoprom.cl.api.backend.Repository.UserRepository;
import com.geoprom.cl.api.backend.models.Request.LoginRequest;
import com.geoprom.cl.api.backend.models.Users;
import com.geoprom.cl.api.backend.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IUserServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(IUserServiceImpl.class.getSimpleName());

    public static UserRepository userRepository;

    public IUserServiceImpl(UserRepository userRepository) {
        IUserServiceImpl.userRepository = userRepository;
    }

    public List<Users> getUsers(Long userId) {
        if (userId != null) {
            Users user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                return Collections.singletonList(user);
            } else {
                return Collections.emptyList();
            }
        } else {
            return userRepository.findAll();
        }
    }

    @Transactional
    public Users createUser(Users user) {
        return userRepository.save(user);
    }

    @Transactional
    public void softDeleteUser(Long userId) {
        userRepository.softDelete(userId);
    }

    @Transactional
    public void activateUser(Long userId) {
        userRepository.activateUser(userId);
    }

    public ResponseEntity<?> findUserByEmail(LoginRequest loginRequest){
        logger.info("findUserByEmail");
        Map<String, Object> response = new HashMap<>();
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Users user =  userRepository.findUserByEmail(email);

        if(user == null){
            logger.info("User not found for this email");
            response.put("message", "User not found for this email");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            if(user.getStatus() == 0){
                logger.info("User inactive");
                response.put("message", "User inactive");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            String passwordEncrypt = encryptPassword(password);
            if(!passwordEncrypt.equals(user.getPassword())){
                logger.info("Incorrect password");
                response.put("message", "Incorrect password");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            String usuario = user.getName();
            logger.info("User correct");
            String token = Utils.createToken(usuario);

            response.put("token", token);
            response.put("error", 1);
            response.put("message", "Usuario logeado");
            response.put("data", user);
            response.put("validUser", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public String encryptPassword(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] inputBytes = password.getBytes("UTF-8");

            byte[] hashBytes = messageDigest.digest(inputBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // En caso de error
    }

}