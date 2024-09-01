package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.models.Request.LoginRequest;
import com.geoprom.cl.api.backend.models.Usuarios;
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

        List<Usuarios> users = usersService.getUsers(user_id);

        logger.info("users" + users.size());
        response.put("users", users);
        response.put("error", 0);
        response.put("message", "Usuarios obtenidos con exito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuarios> createUser(@RequestBody Usuarios user) {
        Usuarios newUser = usersService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }


    @DeleteMapping("/{userId}/soft-delete")
    public ResponseEntity<?> softDeleteUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        usersService.softDeleteUser(userId);
        response.put("message", "Cpf has been successfully eliminated");
        response.put("code", HttpStatus.OK.value());
        response.put("error", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
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


