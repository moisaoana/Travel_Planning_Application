package com.example.demo.dto;

import com.example.demo.model.enums.LocationType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDisplayDTO {
    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private String hours;

    @NotNull
    private String description;

    @NotNull
    private String country;

    @NotNull
    private LocationType locationType;

    @NotNull
    private String image;

    @NotNull
    private String creator_username;

    @NotNull
    private double score;

    @NotNull
    private double nrScores;

    @NotNull
    private List<ReviewDisplayDTO> reviews;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreator_username() {
        return creator_username;
    }

    public void setCreator_username(String creator_username) {
        this.creator_username = creator_username;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getNrScores() {
        return nrScores;
    }

    public void setNrScores(double nrScores) {
        this.nrScores = nrScores;
    }

    public List<ReviewDisplayDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDisplayDTO> reviews) {
        this.reviews = reviews;
    }
}
