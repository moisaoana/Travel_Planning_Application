package com.example.demo.dto;

import com.example.demo.model.Activity;
import com.example.demo.model.Planner;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayDTO {
    @NotNull
    private String date;

    @NotNull
    private List<ActivityDisplayDTO> activities;
}
