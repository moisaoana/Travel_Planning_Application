package com.example.demo.mapper;

import com.example.demo.dto.ActivityDisplayDTO;
import com.example.demo.dto.DayDTO;
import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.model.Activity;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;

import java.util.ArrayList;
import java.util.List;

public class DayMapper {

    public ActivityMapper activityMapper;

    public DayMapper(){
        activityMapper=new ActivityMapper();
    }
    public DayDTO convertToDTO(Day day) {
        List<ActivityDisplayDTO> activityDisplayDTOList=new ArrayList<>();
        for(Activity a: day.getActivities()){
            activityDisplayDTOList.add(activityMapper.convertToDTO(a));
        }
        return DayDTO.builder()
                .date(day.getDate())
                .activities(activityDisplayDTOList)
                .build();
    }
}
