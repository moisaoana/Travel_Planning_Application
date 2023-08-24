package com.example.demo.service;

import com.example.demo.dto.InviteUserDTO;
import com.example.demo.dto.PlannerDTO;
import com.example.demo.model.Planner;
import com.example.demo.model.User;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.PlannerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Planner class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class PlannerService {
    private final static Logger LOGGER = Logger.getLogger(PlannerService.class.getName());

    /**
     * Access the DB through PlannerRepository
     */
    @Autowired
    private PlannerRepository plannerRepository;

    /**
     * Access the DB through UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Method for inserting a new planner in the DB
     * @param plannerDTO DTO fetched from frontend with the planner information
     * @return Warning.SUCCESS(if insertion succeeded)
     *         Warning.DUPLICATE (if the planner name already exists)
     */
    public Warning insertPlanner(PlannerDTO plannerDTO){
        LOGGER.info("Trying to insert a planner in the DB");
        Optional<User> user = userRepository.findByUsername(plannerDTO.getUsername());
        if(user.isPresent()) {
            List<Planner> allPlanners = plannerRepository.findAllByUsersContains(user.get());
            for(Planner p:allPlanners){
                if(p.getName().equals(plannerDTO.getName())){
                    LOGGER.warning("Duplicate planner name!");
                    return Warning.DUPLICATE;
                }
            }
            Planner planner = Planner.builder()
                    .name(plannerDTO.getName())
                    .build();
            List<User> users=new ArrayList<>();
            users.add(user.get());
            planner.setUsers(users);
            user.get().getPlanners().add(planner);
            plannerRepository.save(planner);
            LOGGER.info("Successful insertion");
            return Warning.SUCCESS;
        }
        return null;

    }

    /**
     * Method for retrieving all planners of a user from the DB
     * @param username a String representing the user's username
     * @return list of Planner objects
     */
    public List<Planner> getAllPlanersForUser(String username){
        LOGGER.info("Retrieving all planners of user "+ username);
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> plannerRepository.findAllByUsersContains(value)).orElse(null);
    }

    /**
     * Method for adding a user to a planner
     * @param inviteUserDTO DTO fetched from frontend with the user and planner information
     * @return Warning.SUCCESS(if operation succeeded)
     *         Warning.DUPLICATE (if the planner was already part of the user's list of planners)
     */
    public Warning inviteUser(InviteUserDTO inviteUserDTO){
        LOGGER.info("Method for adding user "+inviteUserDTO.getUsername()+" to planner "+inviteUserDTO.getPlanner().getName());
        Optional<User> user = userRepository.findByUsername(inviteUserDTO.getUsername());
        List<String> allUsers=inviteUserDTO.getPlanner().getUsers();
        List<User> userList=new ArrayList<>();
        for(String u: allUsers){
            userList.add( userRepository.findByUsername(u).get());
        }
        //Optional<Planner> planner=plannerRepository.findByNameAndUsersIn(inviteUserDTO.getPlanner().getName(), Collections.singleton(userList));
        Optional<Planner> planner=plannerRepository.findByName(inviteUserDTO.getPlanner().getName());
        if(!allUsers.contains(inviteUserDTO.getUsername())){
            planner.get().getUsers().add(user.get());
            user.get().getPlanners().add(planner.get());
            plannerRepository.save(planner.get());
            LOGGER.info("Successful operation!");
            return Warning.SUCCESS;
        }else{
            LOGGER.warning("User already invited to this planner!");
            return Warning.DUPLICATE;
        }
    }
}
