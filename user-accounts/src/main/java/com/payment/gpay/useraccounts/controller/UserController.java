package com.payment.gpay.useraccounts.controller;

import com.payment.gpay.common.exceptions.NotFoundException;
import com.payment.gpay.common.models.User;
import com.payment.gpay.useraccounts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = this.userService.addUser(user);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        try {
            User user = this.userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}