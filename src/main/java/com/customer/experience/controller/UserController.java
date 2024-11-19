package com.customer.experience.controller;

import com.customer.experience.dto.UserDetailsDto;
import com.customer.experience.service.ListService;
import com.customer.experience.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserDetailsDto userDetailsDto) {
        try {
            userService.createUser(userDetailsDto);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
