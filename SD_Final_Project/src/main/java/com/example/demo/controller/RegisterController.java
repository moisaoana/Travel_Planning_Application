package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class RegisterController {
    private final static Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        LOGGER.info("POST method for creating a new user: "+userDTO.getFirstName()+", "+userDTO.getLastName()+", "+userDTO.getUsername()+", "+userDTO.getEmail()+", "+userDTO.getType());
        Warning result=userService.insertUser(userDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created");
        }else if(result==Warning.DUPLICATE){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Username exists!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid email!");
        }
    }
}
