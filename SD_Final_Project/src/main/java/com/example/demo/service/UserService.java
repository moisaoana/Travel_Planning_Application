package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class defines the service methods for the User class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class UserService {
    private final static Logger LOGGER = Logger.getLogger(UserService.class.getName());

    /**
     * Access the DB through UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * For encrypting the passwords in the DB
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * For converting UserDTO objects into User objects and vice versa
     */
    private UserMapper userMapper;

    public UserService(){
        bCryptPasswordEncoder=new BCryptPasswordEncoder();
        userMapper = new UserMapper();
        LOGGER.setLevel(Level.INFO);
    }

    /**
     * Method for rretrieving all users from the DB
     * @return list of User objects
     */
    public List<User> getAllUsers(){
        LOGGER.info("Getting all users from the DB");
        return userRepository.findAll();
    }

    /**
     * Method that checks if the username provided by a client at registration has not already been used
     * @param userDTO user information provided by the client at registration
     * @return true (if the username is unique) or false (otherwise)
     */
    public boolean isUsernameUnique(UserDTO userDTO){
        LOGGER.info("Checking if username is unique");
        List<User> allUsers=userRepository.findAll();
        for(User u:allUsers){
            if(u.getUsername().equals(userDTO.getUsername())){
                LOGGER.warning("Duplicate username provided!");
                return false;
            }
        }
        LOGGER.info("Correct username provided!");
        return true;
    }
    /**
     * Method to insert a new User object into the database.
     * @param userDTO user information needed to create the User object
     * @return  Warning.SUCCESS(if insertion succeeded)
     *          Warning.WRONG_FORMAT (if the email is invalid)
     *          Warning.DUPLICATE (if the username already exists)
     */
    public Warning insertUser(UserDTO userDTO){
        LOGGER.info("Trying to insert user in DB");
        String regex="^([a-zA-Z\\d_.]+)(@{1})(yahoo.com|gmail.com){1}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userDTO.getEmail());
        if(isUsernameUnique(userDTO)){
            if(matcher.matches()) {
                userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                User user = userMapper.convertFromDTO(userDTO);
                userRepository.save(user);
                LOGGER.info("Successful insertion");
                return Warning.SUCCESS;
            }else{
                LOGGER.warning("Insertion failed because the email is invalid!");
                return Warning.WRONG_FORMAT;
            }
        }else{
            LOGGER.warning("Insertion failed because the username exists!");
            return Warning.DUPLICATE;
        }
    }

    /**
     * Method for finding the list of locations from the wishlist of a user
     * @param username a String representing the username of the cient
     * @return a list of Location objects
     */
    public List<Location> findWishlist(String username){
        LOGGER.info("Retrieving the wishlist of "+username+" from DB");
        Optional<User> user = userRepository.findByUsername(username);
        return user.get().getLocationsWishlist();
    }
}
