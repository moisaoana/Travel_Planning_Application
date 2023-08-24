package com.example.demo.service;

import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Day class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class CountryService {
    private final static Logger LOGGER = Logger.getLogger(CountryService.class.getName());

    /**
     * Access the DB through CountryRepository
     */
    @Autowired
    private CountryRepository countryRepository;

    /**
     * Method for retrieving all countries from DB
     * @return list of Country objects
     */
    public List<Country> getAllCountries(){
        LOGGER.info("Retrieving all countries from DB");
        return countryRepository.findAll();
    }

    /**
     * Method for inserting a country in the DB
     * @param name a String representing the name of the country
     * @return Warning.SUCCESS(if addition succeeded)
     *         Warning.DUPLICATE (if the country is already present in the DB)
     */
    public Warning insertCountry(String name){
        LOGGER.info("Adding country "+name);
        List<Location> locations=new ArrayList<>();
        List<Country> allCountries=getAllCountries();
        for(Country c: allCountries){
            if(c.getName().equals(name)){
                LOGGER.warning("Country already present in the DB!");
                return Warning.DUPLICATE;
            }
        }
        Country country=Country.builder()
                .name(name)
                .locations(locations)
                .build();
        countryRepository.save(country);
        LOGGER.info("Country successfully added!");
        return Warning.SUCCESS;
    }

    /**
     * Method for deleting a country from DB
     * @param name a String representing the name of the country to be deleted
     * @return Warning.SUCCESS(if deletion succeeded)
     *         Warning.NOT_FOUND (if the country is not present in DB)
     */
    public Warning deleteCountry(String name){
        LOGGER.info("Delete country "+name);
        Optional<Country> country=countryRepository.findByName(name);
        if(country.isPresent()) {
            countryRepository.delete(country.get());
            LOGGER.info("Country deleted!");
            return Warning.SUCCESS;
        }
        LOGGER.warning("Country not present in the DB!");
        return Warning.NOT_FOUND;
    }
}
