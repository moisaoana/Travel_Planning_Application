package com.example.demo.service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.model.Location;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * This class defines the service methods for the Review class.
 * @author Moisa Oana Miruna
 * @version 1.0
 * @since 14.05.2022
 */
@Service
public class ReviewService {
    private final static Logger LOGGER = Logger.getLogger(ReviewService.class.getName());

    /**
     * Access the DB through LocationRepository
     */
    @Autowired
    private LocationRepository locationRepository;

    /**
     * Access the DB through UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Access the DB through ReviewRepository
     */
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Method for saving a Review object in the DB
     * @param reviewDTO DTO fetched from frontend with the review information
     */
    public void saveReview(ReviewDTO reviewDTO){
        LOGGER.info("Saving review in the DB");
        Optional<Location> location=locationRepository.findByName(reviewDTO.getLocation().getName());
        Optional<User> user=userRepository.findByUsername(reviewDTO.getUser());
        String text=reviewDTO.getReview();
        if(location.isPresent() && user.isPresent()) {
            Review review = Review.builder()
                    .text(text)
                    .user(user.get())
                    .location(location.get())
                    .build();
            location.get().getReviews().add(review);
            user.get().getReviews().add(review);
            reviewRepository.save(review);
            LOGGER.info("Successful insertion of review");
        }

    }
}
