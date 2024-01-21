package com.geoprom.cl.api.backend.controller;


import com.geoprom.cl.api.backend.models.Request.LoginRequest;
import com.geoprom.cl.api.backend.models.Users;
import com.geoprom.cl.api.backend.services.Users.IUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class.getSimpleName());

    private final IUserServiceImpl usersService;

    public UserController(IUserServiceImpl usersService) {
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Long user_id) {
        Map<String, Object> response = new HashMap<>();

        List<Users> users = usersService.getUsers(user_id);

        logger.info("users" + users.size());
        response.put("usuarios", users);
        response.put("error", 0);
        response.put("message", "Usuarios obtenidos con exito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users newUser = usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    @PutMapping("/{userId}/soft-delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long userId) {
        usersService.softDeleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId) {
        usersService.activateUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("email: " + loginRequest.getEmail());
        logger.info("password: " + loginRequest.getPassword());

        try {
            return usersService.findUserByEmail(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@RequestBody LoginRequest loginRequest) {
        logger.info("email: " + loginRequest.getEmail());
        logger.info("password: " + loginRequest.getPassword());

        try {
            return usersService.findUserByEmail(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
