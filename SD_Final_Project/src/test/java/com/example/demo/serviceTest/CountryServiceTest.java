package com.example.demo.serviceTest;

import com.example.demo.model.Country;
import com.example.demo.repository.CountryRepository;
import com.example.demo.service.CountryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {
    @InjectMocks
    CountryService countryService;

    @Mock
    CountryRepository countryRepository;

    @Test
    public void insertCountryTestGood(){
        List<Country> allCountries=new ArrayList<>();
        allCountries.add(Country.builder().id(1).name("Romania").build());
        allCountries.add(Country.builder().id(2).name("Scotland").build());
        allCountries.add(Country.builder().id(3).name("Hungary").build());
        when(countryRepository.findAll()).thenReturn(allCountries);
        countryService.insertCountry("England");
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    public void insertCountryTestBad(){
        List<Country> allCountries=new ArrayList<>();
        allCountries.add(Country.builder().id(1).name("Romania").build());
        allCountries.add(Country.builder().id(2).name("Scotland").build());
        allCountries.add(Country.builder().id(3).name("Hungary").build());
        when(countryRepository.findAll()).thenReturn(allCountries);
        countryService.insertCountry("Romania");
        verify(countryRepository,never()).save(any(Country.class));
    }

    @Test
    public void deleteCountryTestGood(){
        Country country=Country.builder().id(1).name("Romania").build();
        when(countryRepository.findByName("Romania")).thenReturn(java.util.Optional.of(country));
        countryService.deleteCountry("Romania");
        verify(countryRepository).delete(any(Country.class));
    }

    @Test
    public void deleteCountryTestBad(){
        when(countryRepository.findByName("Romania")).thenReturn(java.util.Optional.empty());
        countryService.deleteCountry("Romania");
        verify(countryRepository,never()).delete(any(Country.class));
    }
}
