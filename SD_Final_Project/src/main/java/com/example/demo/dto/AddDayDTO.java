package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddDayDTO {

    @NotNull
    private String day;

    @NotNull
    private PlannerDisplayDTO planner;

}
