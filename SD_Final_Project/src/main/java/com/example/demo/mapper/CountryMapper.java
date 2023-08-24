package com.example.demo.mapper;

import com.example.demo.dto.CountryDTO;
import com.example.demo.model.Country;


public class CountryMapper {
    public CountryDTO convertToDTO(Country country){
        return CountryDTO.builder()
                .name(country.getName())
                .build();
    }
}
