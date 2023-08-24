package com.example.demo.serviceTest;

import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.User;
import com.example.demo.model.enums.LocationType;
import com.example.demo.model.enums.UserType;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CountryService;
import com.example.demo.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTest {
    @InjectMocks
    LocationService locationService;

    @Mock
    LocationRepository locationRepository;

    @Mock
    CountryRepository countryRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void isLocationUniqueTestGood(){
        List<Location> allLoc=new ArrayList<>();
        allLoc.add(Location.builder().id(1).name("Midhope Castle").build());
        allLoc.add(Location.builder().id(2).name("Piata Unirii").build());
        allLoc.add(Location.builder().id(3).name("Palatul Parlementului").build());
        when(locationRepository.findAll()).thenReturn(allLoc);
        boolean result=locationService.isLocationUnique("Culloden Battlefield");
        assertTrue(result);
    }

    @Test
    public void isLocationUniqueTestBad(){
        List<Location> allLoc=new ArrayList<>();
        allLoc.add(Location.builder().id(1).name("Midhope Castle").build());
        allLoc.add(Location.builder().id(2).name("Piata Unirii").build());
        allLoc.add(Location.builder().id(3).name("Palatul Parlementului").build());
        when(locationRepository.findAll()).thenReturn(allLoc);
        boolean result=locationService.isLocationUnique("Midhope Castle");
        assertFalse(result);
    }

    @Test
    public void insertLocationGood(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        when(userRepository.findByUsername("oanamoisa")).thenReturn(java.util.Optional.of(user));
        Country country=Country.builder().id(1).name("Scotland").build();
        when(countryRepository.findByName("Scotland")).thenReturn(java.util.Optional.of(country));
        List<Location> allLoc=new ArrayList<>();
        allLoc.add(Location.builder().id(1).name("Midhope Castle").build());
        allLoc.add(Location.builder().id(2).name("Piata Unirii").build());
        allLoc.add(Location.builder().id(3).name("Palatul Parlementului").build());
        when(locationRepository.findAll()).thenReturn(allLoc);
        LocationDTO locationDTO=LocationDTO.builder()
                .name("Culloden Battlefield")
                .address("Abercorn, South Queensferry EH30 9SL, United Kingdom")
                .hours("8 to 10")
                .description("Midhope Castle is a 16th-century tower house in Scotland." )
                .country("Scotland")
                .locationType("ATTRACTION")
                .image("midhope.jpg")
                .creator_username("oanamoisa")
                .build();
        locationService.insertLocation(locationDTO);
        verify(locationRepository).save(any(Location.class));
    }
    @Test
    public void insertLocationBad(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        when(userRepository.findByUsername("oanamoisa")).thenReturn(java.util.Optional.of(user));
        Country country=Country.builder().id(1).name("Scotland").build();
        when(countryRepository.findByName("Scotland")).thenReturn(java.util.Optional.of(country));
        List<Location> allLoc=new ArrayList<>();
        allLoc.add(Location.builder().id(1).name("Midhope Castle").build());
        allLoc.add(Location.builder().id(2).name("Piata Unirii").build());
        allLoc.add(Location.builder().id(3).name("Palatul Parlementului").build());
        when(locationRepository.findAll()).thenReturn(allLoc);
        LocationDTO locationDTO=LocationDTO.builder()
                .name("Midhope Castle")
                .address("Abercorn, South Queensferry EH30 9SL, United Kingdom")
                .hours("8 to 10")
                .description("Midhope Castle is a 16th-century tower house in Scotland." )
                .country("Scotland")
                .locationType("ATTRACTION")
                .image("midhope.jpg")
                .creator_username("oanamoisa")
                .build();
        locationService.insertLocation(locationDTO);
        verify(locationRepository,never()).save(any(Location.class));
    }
    @Test
    public void deleteLocationTestGood(){
        Location location=Location.builder().id(1).name("Midhope Castle").build();
        when(locationRepository.findByName("Midhope Castle")).thenReturn(Optional.of(location));
        Warning result=locationService.deleteLocation("Midhope Castle");
        assertEquals(Warning.SUCCESS,result);
        verify(locationRepository).delete(any(Location.class));
    }

    @Test
    public void deleteLocationTestBad(){
        when(locationRepository.findByName("Midhope Castle")).thenReturn(Optional.empty());
        Warning result=locationService.deleteLocation("Midhope Castle");
        assertEquals(Warning.NOT_FOUND,result);
        verify(locationRepository,never()).delete(any(Location.class));
    }

    @Test
    public void updateLocationScoreTest(){
        Location location=Location.builder().id(1).name("Midhope Castle").score(5).nrScores(1).build();
        when(locationRepository.findByName("Midhope Castle")).thenReturn(Optional.of(location));
        LocationDisplayDTO locationDTO=LocationDisplayDTO.builder()
                .name("Midhope Castle")
                .address("Abercorn, South Queensferry EH30 9SL, United Kingdom")
                .hours("8 to 10")
                .description("Midhope Castle is a 16th-century tower house in Scotland." )
                .country("Scotland")
                .locationType(LocationType.ATTRACTION)
                .image("midhope.jpg")
                .creator_username("oanamoisa")
                .build();
        locationService.updateLocationScore(locationDTO,3);
        verify(locationRepository).save(any(Location.class));

    }

}
