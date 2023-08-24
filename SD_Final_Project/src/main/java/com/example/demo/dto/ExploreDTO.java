package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExploreDTO {
    @NotNull
    List<CountryDTO> countries;

    @NotNull
    List<String> types;

    @NotNull
    List<LocationDisplayDTO> locations;

}
