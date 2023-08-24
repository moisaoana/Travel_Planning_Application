package com.example.demo.controller;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.dto.AddDayDTO;
import com.example.demo.dto.InviteUserDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.ActivityService;
import com.example.demo.service.DayService;
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
public class ViewPlannerController {
    private final static Logger LOGGER = Logger.getLogger(ViewPlannerController.class.getName());

    @Autowired
    private PlannerService plannerService;

    @Autowired
    private DayService dayService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/viewplanner")
    @PreAuthorize("hasRole('USER')")
    public void index(){
        LOGGER.info("Only regular users have access to this page");
    }

    @PostMapping("/invite")
    public ResponseEntity inviteUser(@RequestBody InviteUserDTO inviteUserDTO){
        LOGGER.info("Add user "+ inviteUserDTO.getUsername()+" to planner "+ inviteUserDTO.getPlanner().getName());
        Warning result=plannerService.inviteUser(inviteUserDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User invited");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already invited!");
        }
    }

    @PostMapping("/addDay")
    public ResponseEntity addDay(@RequestBody AddDayDTO addDayDTO){
        LOGGER.info("Add day "+ addDayDTO.getDay()+" to planner "+addDayDTO.getPlanner().getName());
        Warning result=dayService.addDay(addDayDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Day added");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Day already exists!");
        }

    }
    @PostMapping("/addActivity")
    public ResponseEntity addActivity(@RequestBody ActivityDTO activityDTO){
        LOGGER.info("Add activity "+ activityDTO.getActivity()+" to day "+activityDTO.getDay()+" of planner "+ activityDTO.getPlanner().getName());
        Warning result=activityService.addActivity(activityDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Activity added");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Activity already exists!");
        }

    }
}
