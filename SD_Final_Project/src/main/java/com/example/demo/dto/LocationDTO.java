package com.example.demo.dto;

import com.sun.istack.NotNull;
import lombok.Builder;

@Builder
public class LocationDTO {
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
    private String locationType;

    @NotNull
    private String image;

    @NotNull
    private String creator_username;

    public LocationDTO(String name,
                       String address,
                       String hours,
                       String description,
                       String country,
                       String locationType,
                       String image,
                       String creator_username) {
        this.name = name;
        this.address = address;
        this.hours = hours;
        this.description = description;
        this.country = country;
        this.locationType = locationType;
        this.image = image;
        this.creator_username = creator_username;
    }

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

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
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
}
