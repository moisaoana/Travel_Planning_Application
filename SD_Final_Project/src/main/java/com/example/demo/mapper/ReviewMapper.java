package com.example.demo.mapper;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.dto.ReviewDisplayDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Review;
import com.example.demo.model.User;

public class ReviewMapper {
    public ReviewDisplayDTO convertToDTO(Review review){
        return ReviewDisplayDTO.builder()
                .user(review.getUser().getUsername())
                .review(review.getText())
                .build();
    }
}
