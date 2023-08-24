package com.example.demo.serviceTest;

import com.example.demo.dto.AddDayDTO;
import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.PlannerRepository;
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
public class DayServiceTest {
    @InjectMocks
    DayService dayService;

    @Mock
    DayRepository dayRepository;

    @Mock
    PlannerRepository plannerRepository;

    @Test
    public void addDayTest(){
        Planner planner=Planner.builder().id(1).name("Planner1").build();
        planner.setDays(new ArrayList<>());
        when(plannerRepository.findByName("Planner1")).thenReturn(java.util.Optional.of(planner));
        AddDayDTO addDayDTO=AddDayDTO.builder().day("11.05.2022").planner(PlannerDisplayDTO.builder().name("Planner1").users(new ArrayList<>()).days(new ArrayList<>()).build()).build();
        Warning result=dayService.addDay(addDayDTO);
        assertEquals(Warning.SUCCESS,result);
        verify(dayRepository).save(any(Day.class));
    }
}
