package com.example.demo.controller;

import com.example.demo.dto.CountryAndTypesDTO;
import com.example.demo.dto.CountryDTO;
import com.example.demo.dto.LocationDTO;
import com.example.demo.mapper.CountryMapper;
import com.example.demo.model.Country;
import com.example.demo.model.enums.LocationType;
import com.example.demo.model.enums.Warning;
import com.example.demo.service.CountryService;
import com.example.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class AddLocationController {
    private final static Logger LOGGER = Logger.getLogger(AddLocationController.class.getName());

    @Autowired
    private CountryService countryService;

    @Autowired
    private LocationService locationService;

    CountryMapper countryMapper;

    public AddLocationController(){
        countryMapper=new CountryMapper();
    }

    @GetMapping("/addlocation")
    @PreAuthorize("hasRole('CREATOR')")
    public CountryAndTypesDTO getZones(){
        LOGGER.info("GET method for displaying all available countries and types of locations");
        List<Country> countries=countryService.getAllCountries();
        List<CountryDTO> dtos=new ArrayList<>();
        for(Country c: countries){
            dtos.add(countryMapper.convertToDTO(c));
        }
        LocationType[] locations=LocationType.values();
        List<String> loc=new ArrayList<>();
        for (LocationType location : locations) {
            loc.add(location.toString());
        }
        return new CountryAndTypesDTO(dtos,loc);
    }

    @PostMapping("/addlocation")
    public ResponseEntity createLocation(@RequestBody LocationDTO locationDTO){
        LOGGER.info("Location added by creator: "+locationDTO.getName()+", "+locationDTO.getLocationType()+", "+locationDTO.getDescription()+", "+locationDTO.getHours()+", "+locationDTO.getAddress()+", "+locationDTO.getCountry()+", "+locationDTO.getCreator_username()+", "+locationDTO.getImage());
        Warning result=locationService.insertLocation(locationDTO);
        if(result==Warning.SUCCESS) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Location created");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Location already created!");
        }

    }
}
