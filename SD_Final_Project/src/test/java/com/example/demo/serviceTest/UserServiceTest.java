package com.example.demo.serviceTest;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.model.enums.UserType;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;


    @Mock
    UserRepository userRepository;


    @Test
    public void isUsernameUniqueTest(){
        List<User> data=new ArrayList<>();
        UserDTO userDTO=new UserDTO("Alexandra","Moisa","alemoisa","ale", UserType.ROLE_USER,"alemoisa@gmail.com");
        data.add(new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com"));
        data.add(new User(2,"Ioana","Ghete","ioanaghete","oanaioana", UserType.ROLE_CREATOR,"ioana.ghete@gmail.com"));
        data.add(new User(3,"Raluca","Rusu","ralucarusu","raluca", UserType.ROLE_USER,"ralu.rusu@gmail.com"));
        when(userRepository.findAll()).thenReturn(data);
        boolean isUnique=userService.isUsernameUnique(userDTO);
        assertTrue(isUnique);
    }

    @Test
    public void isUsernameNotUniqueTest(){
        List<User> data=new ArrayList<>();
        UserDTO userDTO=new UserDTO("Alexandra","Moisa","oanamoisa","ale",UserType.ROLE_USER,"alemoisa@gmail.com");
        data.add(new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com"));
        data.add(new User(2,"Ioana","Ghete","ioanaghete","oanaioana", UserType.ROLE_CREATOR,"ioana.ghete@gmail.com"));
        data.add(new User(3,"Raluca","Rusu","ralucarusu","raluca", UserType.ROLE_ADMIN,"ralu.rusu@gmail.com"));
        when(userRepository.findAll()).thenReturn(data);
        boolean isUnique=userService.isUsernameUnique(userDTO);
        assertFalse(isUnique);
    }

    @Test
    public void insertUserTest(){
        List<User> data=new ArrayList<>();
        data.add(new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com"));
        data.add(new User(2,"Ioana","Ghete","ioanaghete","ioana", UserType.ROLE_CREATOR,"ioana.ghete@gmail.com"));
        data.add(new User(3,"Raluca","Rusu","ralucarusu","raluca", UserType.ROLE_USER,"ralu.rusu@gmail.com"));
        when(userRepository.findAll()).thenReturn(data);
        UserDTO userDTO=new UserDTO("Alexandra","Moisa","alemoisa","ale",UserType.ROLE_USER,"alemoisa@gmail.com");
        Warning result=userService.insertUser(userDTO);
        assertEquals(Warning.SUCCESS,result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void insertDuplicateUserTest(){
        List<User> data=new ArrayList<>();
        data.add(new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com"));
        data.add(new User(2,"Ioana","Ghete","ioanaghete","oanaioana", UserType.ROLE_CREATOR,"ioana.ghete@gmail.com"));
        data.add(new User(3,"Raluca","Rusu","ralucarusu","raluca", UserType.ROLE_USER,"ralu.rusu@gmail.com"));
        when(userRepository.findAll()).thenReturn(data);
        UserDTO userDTO=new UserDTO("Alexandra","Moisa","oanamoisa","ale",UserType.ROLE_USER,"alemoisa@gmail.com");
        Warning result=userService.insertUser(userDTO);
        assertEquals(Warning.DUPLICATE,result);
        verify(userRepository,never()).save(any(User.class));
    }

    @Test
    public void inserUserInvalidEmailTest(){
        List<User> data=new ArrayList<>();
        data.add(new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com"));
        data.add(new User(2,"Ioana","Ghete","ioanaghete","oanaioana", UserType.ROLE_CREATOR,"ioana.ghete@gmail.com"));
        data.add(new User(3,"Raluca","Rusu","ralucarusu","raluca", UserType.ROLE_USER,"ralu.rusu@gmail.com"));
        when(userRepository.findAll()).thenReturn(data);
        UserDTO userDTO=new UserDTO("Alexandra","Moisa","alemoisa","ale",UserType.ROLE_USER,"alemoisagmail.com");
        Warning result=userService.insertUser(userDTO);
        assertEquals(Warning.WRONG_FORMAT,result);
        verify(userRepository,never()).save(any(User.class));
    }


}
