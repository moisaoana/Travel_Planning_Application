package com.example.demo.dto;

import jdk.jfr.Name;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LocationWithScoreDTO {

    @NotNull
    private LocationDisplayDTO location;

    @NotNull
    private int score;
}
