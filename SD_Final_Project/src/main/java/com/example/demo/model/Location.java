package com.example.demo.model;

import com.example.demo.model.enums.LocationType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name="location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique= true,nullable=false)
    private String name;

    @Column(nullable=false)
    private String address;

    @Column(nullable=false)
    private double score;

    @Column(nullable=false)
    private double nrScores;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private String hours;

    @Column(nullable=false)
    private LocationType locationType;

    @Column(nullable=false)
    private String image;

    @ManyToOne
    @JoinColumn(name="country_id", nullable=false)
    private Country country;

    @ManyToOne
    @JoinColumn(name="creator_id", nullable=false)
    private User creator;

    @ManyToMany(mappedBy = "locationsWishlist")
    private List<User> usersWishList;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="location")
    private List<Review> reviews;

    public Location(){
    }

    public Location(Integer id,
                    String name,
                    String address,
                    double score,
                    double nrScores,
                    String description,
                    String hours,
                    LocationType locationType,
                    String image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.score = score;
        this.nrScores = nrScores;
        this.description = description;
        this.hours = hours;
        this.locationType = locationType;
        this.image=image;
    }

    public Location(String name,
                    String address,
                    double score,
                    double nrScores,
                    String description,
                    String hours,
                    LocationType locationType,
                    String image) {
        this.name = name;
        this.address = address;
        this.score = score;
        this.nrScores = nrScores;
        this.description = description;
        this.hours = hours;
        this.locationType = locationType;
        this.image=image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getUsersWishList() {
        return usersWishList;
    }

    public void setUsersWishList(List<User> usersWishList) {
        this.usersWishList = usersWishList;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
