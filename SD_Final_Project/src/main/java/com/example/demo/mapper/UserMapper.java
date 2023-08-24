package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

public class UserMapper {
    public UserDTO convertToDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
    public User convertFromDTO(UserDTO userDTO){
       return User.builder()
               .firstName(userDTO.getFirstName())
               .lastName(userDTO.getLastName())
               .username(userDTO.getUsername())
               .password(userDTO.getPassword())
               .type(userDTO.getType())
               .email(userDTO.getEmail())
               .build();
    }
}
