package com.example.demo.dto;

import com.example.demo.model.Activity;
import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    @NotNull
    private String activity;

    @NotNull
    private int startH;

    @NotNull
    private int endH;

    @NotNull
    private String day;

    @NotNull
    private PlannerDisplayDTO planner;


}
