package com.example.demo.controller;


import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.mapper.PlannerMapper;
import com.example.demo.model.Planner;
import com.example.demo.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ViewPlannersController {
    private final static Logger LOGGER = Logger.getLogger(ViewPlannersController.class.getName());

    @Autowired
    private PlannerService plannerService;

    private PlannerMapper plannerMapper;

    public ViewPlannersController(){
        plannerMapper=new PlannerMapper();
    }


    @GetMapping("/viewplanners/{username}")
    @PreAuthorize("hasRole('USER')")
    public List<PlannerDisplayDTO> getAllPlanersForUser(@PathVariable String username){
        LOGGER.info("GET all planners of user "+username);
        List<Planner> plannerList=plannerService.getAllPlanersForUser(username);
        List<PlannerDisplayDTO> dtos=new ArrayList<>();
        for(Planner p: plannerList){
            dtos.add(plannerMapper.convertToDTO(p));
        }
        return dtos;
    }
}
