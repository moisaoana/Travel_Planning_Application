package com.example.demo.controller;

import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.Location;
import com.example.demo.service.LocationService;
import com.example.demo.service.UserService;
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
public class WishlistController {
    private final static Logger LOGGER = Logger.getLogger(WishlistController.class.getName());

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    private LocationMapper locationMapper;

    public WishlistController(){
        locationMapper=new LocationMapper();
    }

    @GetMapping("/wishlist/{username}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<LocationDisplayDTO> getWishlist(@PathVariable String username){
        LOGGER.info("GET method for displaying all locations from wishlist of user "+username);
        List<Location> locationList=userService.findWishlist(username);
        List<LocationDisplayDTO> dtos=new ArrayList<>();
        for(Location loc:locationList){
            dtos.add(locationMapper.convertToDTO(loc));
        }
        return dtos;
    }
}
