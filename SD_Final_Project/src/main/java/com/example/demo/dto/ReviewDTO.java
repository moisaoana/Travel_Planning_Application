package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewDTO {
    @NotNull
    private LocationDisplayDTO location;

    @NotNull
    private String user;

    @NotNull
    private String review;
}
