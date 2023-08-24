package com.example.demo.dto;

import com.example.demo.model.Day;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDisplayDTO {

    @NotNull
    private String name;

    @NotNull
    private int startHour;

    @NotNull
    private int endHour;

}
