package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class CreatorProfileController {
    private final static Logger LOGGER = Logger.getLogger(CreatorProfileController.class.getName());

    @GetMapping("/creatorprofile")
    @PreAuthorize("hasRole('CREATOR')")
    public void index(){
        LOGGER.info("Only creators have access to this page");
    }
}
