package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Location class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class LocationService {
    private final static Logger LOGGER = Logger.getLogger(LocationService.class.getName());

    /**
     * Access the DB through LocationRepository
     */
    @Autowired
    private LocationRepository locationRepository;

    /**
     * Access the DB through UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Access the DB through CountryRepository
     */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * For converting DTO objects into Location objects and vice versa
     */
    private LocationMapper locationMapper;

    public LocationService(){
        locationMapper=new LocationMapper();
    }

    /**
     * Method for checking if a location name is unique
     * @param name a String representing the name of the location
     * @return true(is location is unique) or false(otherwise)
     */
    public boolean isLocationUnique(String name){
        LOGGER.info("Checking if location name is unique");
        List<Location> allLocations=locationRepository.findAll();
        for(Location l:allLocations){
            if(l.getName().equals(name)){
                LOGGER.warning("Duplicate location provided!");
                return false;
            }
        }
        LOGGER.info("Correct location name provided!");
        return true;
    }

    /**
     * Method for inserting a new location in the DB
     * @param locationDTO DTO fetched from frontend with the location information
     * @return  Warning.SUCCESS(if insertion succeeded)
     *          Warning.WRONG_NOT_FOUND (if other errors occur)
     *          Warning.DUPLICATE (if a location with the same name already exists)
     */
    public Warning insertLocation(LocationDTO locationDTO) {
        LOGGER.info("Trying to insert location in DB");
        Optional<User> creator = userRepository.findByUsername(locationDTO.getCreator_username());
        Optional<Country> country = countryRepository.findByName(locationDTO.getCountry());
        if (creator.isPresent() && country.isPresent()) {
            if(isLocationUnique(locationDTO.getName())) {
                Location location = locationMapper.convertFromDTO(locationDTO, creator.get(), country.get());
                locationRepository.save(location);
                LOGGER.info("Successful insertion");
                return Warning.SUCCESS;
            }else{
                LOGGER.warning("Insertion failed because the location already exists!");
                return Warning.DUPLICATE;
            }
        }else{
            LOGGER.warning("Insertion failed because country or creator doesn't exist!");
            return Warning.NOT_FOUND;
        }
    }

    /**
     * Method for getting all locations of a creator
     * @param username a String representing the creator's username
     * @return a list of Location objects
     */
    public List<Location> findAllLocationsForCreator(String username){
        LOGGER.info("Retrieving all locations of creator "+username );
       return  locationRepository.findAllByCreator_Username(username);
    }

    /**
     * Method for retrieving all locations from DB
     * @return list of Location objects
     */
    public List<Location> findAllLocations(){
        LOGGER.info("Retrieving all locations from DB");
        return  locationRepository.findAll();
    }

    /**
     * Method for changing the score of a location
     * @param locationDisplayDTO DTO fetched from frontend with the location information
     * @param score the score given to a location by a user
     */
    public void updateLocationScore(LocationDisplayDTO locationDisplayDTO, int score){
        LOGGER.info("Adding score "+score+" to location "+locationDisplayDTO.getName());
        Optional<Location> location=locationRepository.findByName(locationDisplayDTO.getName());
        if(location.isPresent()){
            double scoreBefore=location.get().getScore();
            double nrScores=location.get().getNrScores();
            nrScores++;
            double scoreAfter=(scoreBefore+score)/nrScores;
            location.get().setScore(scoreAfter);
            location.get().setNrScores(nrScores);
            locationRepository.save(location.get());
            LOGGER.info("Updated score of location "+locationDisplayDTO.getName());
        }
    }

    /**
     * Method for adding a location to a user's wishlist
     * @param wishListDTO DTO fetched from frontend with the location and user information
     * @return Warning.SUCCESS(if operation succeeded)
     *         Warning.DUPLICATE (if the location was already part of the user's wishlist)
     */
    public Warning addToWishlist(WishListDTO wishListDTO){
        LOGGER.info("Adding location "+ wishListDTO.getLocation().getName()+" to wishlist of user "+wishListDTO.getUser());
        Optional<Location> location=locationRepository.findByName(wishListDTO.getLocation().getName());
        Optional<User> user = userRepository.findByUsername(wishListDTO.getUser());

        if(user.isPresent() && location.isPresent()){
            List<Location> currentWishlist=user.get().getLocationsWishlist();
            if(currentWishlist.contains(location.get())){
                LOGGER.info("Location successfully added to wishlist");
                return Warning.DUPLICATE;
            }else {
                user.get().getLocationsWishlist().add(location.get());
                location.get().getUsersWishList().add(user.get());
                locationRepository.save(location.get());
                LOGGER.warning("Location already in wishlist!");
                return Warning.SUCCESS;
            }
        }
        return null;
    }

    /**
     * Method for deleting a location from DB
     * @param name a String representing the name of the location to be deleted
     * @return Warning.SUCCESS(if deletion succeeded)
     *         Warning.NOT_FOUND (if the location doesn't exist)
     */
    public Warning deleteLocation(String name){
        LOGGER.info("Deleting location "+name);
        Optional<Location> location=locationRepository.findByName(name);
        if(location.isPresent()){
            locationRepository.delete(location.get());
            LOGGER.info("Location successfully deleted!");
            return Warning.SUCCESS;
        }else{
            LOGGER.warning("Location not present!");
            return Warning.NOT_FOUND;
        }
    }
}
