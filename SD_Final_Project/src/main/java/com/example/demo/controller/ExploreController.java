package com.example.demo.controller;

import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.ExploreDTO;
import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.mapper.CountryMapper;
import com.example.demo.mapper.LocationMapper;
import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.enums.LocationType;
import com.example.demo.service.CountryService;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ExploreController {
    private final static Logger LOGGER = Logger.getLogger(ExploreController.class.getName());

    @Autowired
    private LocationService locationService;

    private LocationMapper locationMapper;

    @Autowired
    private CountryService countryService;

    CountryMapper countryMapper;

    public ExploreController(){
        locationMapper=new LocationMapper();
        countryMapper=new CountryMapper();
    }

    @GetMapping("/explore")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public ExploreDTO getAllLocations(){
        LOGGER.info("GET method for displaying all locations");
        List<Location> locationList=locationService.findAllLocations();
        List<LocationDisplayDTO> dtos=new ArrayList<>();
        for(Location loc:locationList){
            dtos.add(locationMapper.convertToDTO(loc));
        }
        List<Country> countries=countryService.getAllCountries();
        List<CountryDTO> dtosCountries=new ArrayList<>();
        for(Country c: countries){
            dtosCountries.add(countryMapper.convertToDTO(c));
        }
        dtosCountries.add(new CountryDTO("ALL"));
        LocationType[] locations=LocationType.values();
        List<String> loc=new ArrayList<>();
        for (LocationType location : locations) {
            loc.add(location.toString());
        }
        loc.add("ALL");
        return ExploreDTO.builder()
                .locations(dtos)
                .countries(dtosCountries)
                .types(loc)
                .build();
    }
}
