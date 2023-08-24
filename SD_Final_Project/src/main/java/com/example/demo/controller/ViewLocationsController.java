package com.example.demo.controller;

import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.Location;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ViewLocationsController {
    private final static Logger LOGGER = Logger.getLogger(ViewLocationsController.class.getName());

    @Autowired
    private LocationService locationService;

    private LocationMapper locationMapper;

    public ViewLocationsController(){
        locationMapper=new LocationMapper();
    }

    @GetMapping("/viewcreator/{username}")
    @PreAuthorize("hasRole('CREATOR')")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDisplayDTO> getAllLocationsForCreator(@PathVariable String username){
        LOGGER.info("GET method for displaying all locations of creator "+username);
        List<Location> locationList=locationService.findAllLocationsForCreator(username);
        List<LocationDisplayDTO> dtos=new ArrayList<>();
        for(Location loc:locationList){
            dtos.add(locationMapper.convertToDTO(loc));
        }
        return dtos;
    }
}
