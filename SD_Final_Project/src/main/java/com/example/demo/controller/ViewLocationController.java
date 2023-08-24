package com.example.demo.controller;

import com.example.demo.dto.LocationWithScoreDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.WishListDTO;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.LocationService;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class ViewLocationController {
    private final static Logger LOGGER = Logger.getLogger(ViewLocationController.class.getName());

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/viewlocation")
    @PreAuthorize("hasRole('USER')  or hasRole('CREATOR')")
    public void index(){
        LOGGER.info("Only regular users and creators have access to this page");
    }


    @PutMapping("/viewlocation")
    public ResponseEntity updateLocationScore(@RequestBody LocationWithScoreDTO locationWithScoreDTO){
        LOGGER.info("PUT method for updating the score of the location");
        locationService.updateLocationScore(locationWithScoreDTO.getLocation(),locationWithScoreDTO.getScore());
        return ResponseEntity.status(HttpStatus.OK)
                .body("Location updated");
    }

    @PostMapping("/viewlocation")
    public ResponseEntity giveReview(@RequestBody ReviewDTO reviewDTO){
        LOGGER.info("POST method for saving a review");
        reviewService.saveReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Review saved");
    }

    @PostMapping("/wishlist")
    public ResponseEntity addToWishlist(@RequestBody WishListDTO wishListDTO){
        LOGGER.info("POST method for adding a location to wishlist");
        Warning result=locationService.addToWishlist(wishListDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Added to wishlist");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Already exists!");
        }
    }

    @DeleteMapping("/deletelocation")
    public ResponseEntity deleteLocation(@RequestBody String name){
        LOGGER.info("POST method for deleting location "+name);
        Warning result=locationService.deleteLocation(name);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("LocationDeleted");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error!");
        }
    }
}
