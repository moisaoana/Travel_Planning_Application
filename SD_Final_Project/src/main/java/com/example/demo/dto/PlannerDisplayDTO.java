package com.example.demo.dto;

import com.example.demo.model.Day;
import com.example.demo.model.User;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlannerDisplayDTO {

    @NotNull
    private String name;

    @NotNull
    private List<String> users;

    @NotNull
    private List<DayDTO>days;
}
