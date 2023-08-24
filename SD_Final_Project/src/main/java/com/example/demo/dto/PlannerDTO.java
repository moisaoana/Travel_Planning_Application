package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDTO {

    @NotNull
    private String name;

    @NotNull
    private String username;
}
