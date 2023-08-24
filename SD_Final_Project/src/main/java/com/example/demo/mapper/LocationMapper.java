package com.example.demo.mapper;

import com.example.demo.dto.LocationDTO;
import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.dto.ReviewDisplayDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Country;
import com.example.demo.model.Location;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.model.enums.LocationType;

import java.util.ArrayList;
import java.util.List;

public class LocationMapper {

    private ReviewMapper reviewMapper;

    public LocationMapper(){
        reviewMapper=new ReviewMapper();
    }

    public Location convertFromDTO(LocationDTO locationDTO, User creator, Country country){
        return Location.builder()
                .name(locationDTO.getName())
                .address(locationDTO.getAddress())
                .score(0)
                .nrScores(0)
                .description(locationDTO.getDescription())
                .hours(locationDTO.getHours())
                .locationType(LocationType.valueOf(locationDTO.getLocationType()))
                .image(locationDTO.getImage())
                .country(country)
                .creator(creator)
                .build();
    }
    public LocationDisplayDTO convertToDTO(Location location){
        List<ReviewDisplayDTO> reviews=new ArrayList<>();
        for(Review r: location.getReviews()){
            reviews.add(reviewMapper.convertToDTO(r));
        }
        return LocationDisplayDTO.builder()
                .name(location.getName())
                .address(location.getAddress())
                .hours(location.getHours())
                .description(location.getDescription())
                .country(location.getCountry().getName())
                .locationType(location.getLocationType())
                .image(location.getImage())
                .creator_username(location.getCreator().getUsername())
                .score(location.getScore())
                .nrScores(location.getNrScores())
                .reviews(reviews)
                .build();
    }


}
