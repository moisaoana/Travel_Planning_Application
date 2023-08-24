package com.example.demo.service;

import com.example.demo.dto.AddDayDTO;
import com.example.demo.model.Activity;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Day class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class DayService {
    private final static Logger LOGGER = Logger.getLogger(DayService.class.getName());

    /**
     * Access the DB through DayRepository
     */
    @Autowired
    private DayRepository dayRepository;

    /**
     * Access the DB through PlannerRepository
     */
    @Autowired
    private PlannerRepository plannerRepository;


    /**
     * Method for adding a day to a planner
     * @param addDayDTO DTO fetched from frontend with the day information
     * @return Warning.SUCCESS(if addition succeeded)
     *         Warning.DUPLICATE (if the day is already present in the planner)
     */
    public Warning addDay(AddDayDTO addDayDTO){
        LOGGER.info("Adding day "+addDayDTO.getDay()+" to planner "+addDayDTO.getPlanner().getName());
        Optional<Planner> planner=plannerRepository.findByName(addDayDTO.getPlanner().getName());
        List<Day> allDaysPlanner=dayRepository.getAllByPlanner(planner.get());
        for(Day d:allDaysPlanner){
            if(d.getDate().equals(addDayDTO.getDay())){
                LOGGER.warning("Day already present in the planner!");
                return Warning.DUPLICATE;
            }
        }
        List<Activity> activites=new ArrayList<>();
        Day day= Day.builder()
                    .date(addDayDTO.getDay())
                    .planner(planner.get())
                    .activities(activites)
                    .build();
        planner.get().getDays().add(day);
        dayRepository.save(day);
        LOGGER.info("Day added successfully!");
        return  Warning.SUCCESS;
    }

}
