package com.example.demo.serviceTest;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.dto.ActivityDisplayDTO;
import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.model.Activity;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.PlannerRepository;
import com.example.demo.service.ActivityService;
import com.example.demo.service.DayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {

    @InjectMocks
    ActivityService activityService;

    @Mock
    ActivityRepository activityRepository;

    @Mock
    PlannerRepository plannerRepository;

    @Mock
    DayRepository dayRepository;

    @Test
    public void addActivity(){
        Planner planner=Planner.builder().id(1).name("Planner1").build();
        planner.setDays(new ArrayList<>());
        when(plannerRepository.findByName("Planner1")).thenReturn(java.util.Optional.of(planner));
        Day day=Day.builder().id(1).date("11.05.2022").planner(planner).activities(new ArrayList<>()).build();
        when(dayRepository.findByDateAndPlanner("11.05.2022",planner)).thenReturn(java.util.Optional.of(day));
        when(activityRepository.findAllByDay(day)).thenReturn(new ArrayList<>());
        PlannerDisplayDTO plannerDisplayDTO=PlannerDisplayDTO.builder().name("Planner1").build();
        ActivityDTO activityDTO=ActivityDTO.builder().activity("Visit").startH(8).endH(10).planner(plannerDisplayDTO).day("11.05.2022").build();
        Warning result=activityService.addActivity(activityDTO);
        assertEquals(Warning.SUCCESS,result);
        verify(activityRepository).save(any(Activity.class));

    }

}
