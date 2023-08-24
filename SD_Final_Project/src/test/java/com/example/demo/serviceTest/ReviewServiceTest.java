package com.example.demo.serviceTest;


import com.example.demo.dto.LocationDisplayDTO;
import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Location;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.model.enums.LocationType;
import com.example.demo.model.enums.UserType;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    LocationRepository locationRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void saveReviewTest(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        Location location=Location.builder()
                .id(1)
                .name("Midhope Castle")
                .address("Abercorn, South Queensferry EH30 9SL, United Kingdom")
                .score(4)
                .nrScores(5)
                .description("Midhope Castle is a 16th-century tower house in Scotland." )
                .hours("8 to 10")
                .locationType(LocationType.ATTRACTION)
                .image("midhope.jpg")
                .country(null)
                .creator(null)
                .usersWishList(null)
                .reviews(new ArrayList<>())
                .build();
        Review review=Review.builder()
                .id(1)
                .text("review 1")
                .user(user)
                .location(location)
                .build();
        List<Review> reviews=new ArrayList<>();
        reviews.add(review);
        location.getReviews().add(review);
        user.setReviews(reviews);
        when(locationRepository.findByName("Midhope Castle")).thenReturn(java.util.Optional.of(location));
        when(userRepository.findByUsername("oanmoisa")).thenReturn(java.util.Optional.of(user));


        LocationDisplayDTO locationDisplayDTO=LocationDisplayDTO.builder()
                .name("Midhope Castle")
                .address("Abercorn, South Queensferry EH30 9SL, United Kingdom")
                .hours("8 to 10")
                .description("Midhope Castle is a 16th-century tower house in Scotland." )
                .country("Scotland")
                .locationType(LocationType.ATTRACTION)
                .image("midhope.jpg")
                .creator_username("alemoisa")
                .score(4)
                .nrScores(5)
                .reviews(new ArrayList<>())
                .build();

        ReviewDTO reviewDTO=ReviewDTO.builder()
                .location(locationDisplayDTO)
                .user("oanmoisa")
                .review("Great place")
                .build();
        reviewService.saveReview(reviewDTO);
        verify(reviewRepository).save(any(Review.class));
    }

}
