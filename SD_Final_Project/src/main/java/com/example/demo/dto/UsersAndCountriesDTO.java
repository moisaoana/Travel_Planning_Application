package com.example.demo.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersAndCountriesDTO {

    @NotNull
    private List<String> users;

    @NotNull
    private List<CountryDTO> countries;

}
