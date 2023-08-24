package com.example.demo.service;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.model.Activity;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.PlannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Activity class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class ActivityService {
    private final static Logger LOGGER = Logger.getLogger(ActivityService.class.getName());

    /**
     * Access the DB through ActivityRepository
     */
    @Autowired
    private ActivityRepository activityRepository;

    /**
     * Access the DB through PlannerRepository
     */
    @Autowired
    private PlannerRepository plannerRepository;

    /**
     * Access the DB through DayRepository
     */
    @Autowired
    private DayRepository dayRepository;

    /**
     * Method for adding activity to day in planner
     * @param activityDTO DTO fetched from frontend with the activity information
     * @return Warning.SUCCESS(if addition succeeded)
     *         Warning.DUPLICATE (if the activity is already presented in DB)
     */
    public Warning addActivity(ActivityDTO activityDTO){
        LOGGER.info("Adding activity "+ activityDTO.getActivity()+" to day "+activityDTO.getDay()+" of planner "+activityDTO.getPlanner().getName());
        Optional<Planner> planner=plannerRepository.findByName(activityDTO.getPlanner().getName());
        Optional<Day> day=dayRepository.findByDateAndPlanner(activityDTO.getDay(),planner.get());
        List<Activity> allActivitiesDay=activityRepository.findAllByDay(day.get());

        for(Activity a: allActivitiesDay){
            if(a.getName().equals(activityDTO.getActivity())){
                LOGGER.warning("Activity already exists!");
                return Warning.DUPLICATE;
            }
        }
       Activity activity= Activity.builder()
                                    .name(activityDTO.getActivity())
                                    .startHour(activityDTO.getStartH())
                                    .endHour(activityDTO.getEndH())
                                    .day(day.get())
                                    .build();
        day.get().getActivities().add(activity);
        activityRepository.save(activity);
        LOGGER.info("Activity added successfully!");
        return  Warning.SUCCESS;
    }
}
