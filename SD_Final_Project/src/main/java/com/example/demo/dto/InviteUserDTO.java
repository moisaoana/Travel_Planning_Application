package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InviteUserDTO {
    @NotNull
    private String username;

    @NotNull
    private PlannerDisplayDTO planner;


}
