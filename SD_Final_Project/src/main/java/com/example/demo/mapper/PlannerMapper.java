package com.example.demo.mapper;

import com.example.demo.dto.DayDTO;
import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class PlannerMapper {

    private DayMapper dayMapper;

    public PlannerMapper(){
        dayMapper=new DayMapper();
    }

    public PlannerDisplayDTO convertToDTO(Planner planner){
        List<String> users=new ArrayList<>();
        for(User u: planner.getUsers()){
            users.add(u.getUsername());
        }
        List<DayDTO> days=new ArrayList<>();
        for(Day d: planner.getDays()){
            days.add(dayMapper.convertToDTO(d));
        }
        return PlannerDisplayDTO.builder()
                .name(planner.getName())
                .users(users)
                .days(days)
                .build();
    }
}
