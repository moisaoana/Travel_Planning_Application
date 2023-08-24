package com.example.demo.controller;

import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.UsersAndCountriesDTO;
import com.example.demo.mapper.CountryMapper;
import com.example.demo.model.Country;
import com.example.demo.model.User;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.CountryService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class AdminProfileController {
    private final static Logger LOGGER = Logger.getLogger(AdminProfileController.class.getName());

    @Autowired
    private CountryService countryService;

    @Autowired
    private UserService userService;

    private CountryMapper countryMapper;

    public AdminProfileController(){
        countryMapper=new CountryMapper();
    }

    @GetMapping("/adminprofile")
    @PreAuthorize("hasRole('ADMIN')")
    public UsersAndCountriesDTO index(){
        LOGGER.info("GET method for displaying all users and all countries from DB");
        List<Country> countries=countryService.getAllCountries();
        List<CountryDTO> dtos=new ArrayList<>();
        for(Country c: countries){
            dtos.add(countryMapper.convertToDTO(c));
        }
        List<User> allUsers=userService.getAllUsers();
        List<String> usernames=new ArrayList<>();
        for(User u: allUsers){
            usernames.add(u.getUsername());
        }
        return UsersAndCountriesDTO.builder()
                .users(usernames)
                .countries(dtos)
                .build();
    }

    @PostMapping("/addcountry")
    public ResponseEntity addCountry(@RequestBody String country){
        LOGGER.info("POST method for adding country "+country);
        Warning result=countryService.insertCountry(country);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Country created");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Country already exists!");
        }

    }
    @DeleteMapping("/deletecountry")
    public ResponseEntity deleteCountry(@RequestBody String country){
        LOGGER.info("Delete country "+country);
        Warning result=countryService.deleteCountry(country);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Country delete");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Country doesn't exist!");
        }

    }
}
