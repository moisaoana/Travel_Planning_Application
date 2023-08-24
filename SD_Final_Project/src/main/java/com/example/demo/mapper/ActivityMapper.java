package com.example.demo.mapper;

import com.example.demo.dto.ActivityDisplayDTO;
import com.example.demo.dto.DayDTO;
import com.example.demo.model.Activity;
import com.example.demo.model.Day;

public class ActivityMapper {
    public ActivityDisplayDTO convertToDTO(Activity activity) {
        return ActivityDisplayDTO.builder()
                .name(activity.getName())
                .startHour(activity.getStartHour())
                .endHour(activity.getEndHour())
                .build();

    }
}
