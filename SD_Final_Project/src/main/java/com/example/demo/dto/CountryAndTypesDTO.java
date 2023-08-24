package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
public class CountryAndTypesDTO {
    @NotNull
    List<CountryDTO> countries;

    @NotNull
    List<String> types;

    public CountryAndTypesDTO(List<CountryDTO> countries, List<String> types) {
        this.countries = countries;
        this.types = types;
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
