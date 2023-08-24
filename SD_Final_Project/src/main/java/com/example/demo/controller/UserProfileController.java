package com.example.demo.controller;

import com.example.demo.dto.PlannerDTO;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserProfileController {
    private final static Logger LOGGER = Logger.getLogger(UserProfileController.class.getName());

    @Autowired
    private PlannerService plannerService;

    @GetMapping("/userprofile")
    @PreAuthorize("hasRole('USER')")
    public void index(){
        LOGGER.info("Only regular users have access to this page");
    }

    @PostMapping("/userprofile")
    public ResponseEntity createPlanner(@RequestBody PlannerDTO plannerDTO){
        LOGGER.info("Add planer named "+plannerDTO.getName());
        Warning result=plannerService.insertPlanner(plannerDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Planner created");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Planner already created!");
        }

    }
}
