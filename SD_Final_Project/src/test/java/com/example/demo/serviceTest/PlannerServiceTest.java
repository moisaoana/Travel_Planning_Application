package com.example.demo.serviceTest;

import com.example.demo.dto.InviteUserDTO;
import com.example.demo.dto.PlannerDTO;
import com.example.demo.dto.PlannerDisplayDTO;
import com.example.demo.model.Location;
import com.example.demo.model.Planner;
import com.example.demo.model.User;
import com.example.demo.model.enums.UserType;
import com.example.demo.model.enums.Warning;
import com.example.demo.repository.PlannerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PlannerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlannerServiceTest {

    @InjectMocks
    PlannerService plannerService;

    @Mock
    PlannerRepository plannerRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void insertPlannerTestGood(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        user.setPlanners(new ArrayList<>());
        when(userRepository.findByUsername("oanamoisa")).thenReturn(java.util.Optional.of(user));
        List<Planner> allPlanners=new ArrayList<>();
        allPlanners.add(Planner.builder().id(1).name("Planner1").build());
        allPlanners.add(Planner.builder().id(2).name("Planner2").build());
        when(plannerRepository.findAllByUsersContains(user)).thenReturn(allPlanners);
        PlannerDTO plannerDTO=PlannerDTO.builder()
                .name("Planner3")
                .username("oanamoisa")
                .build();
        Warning result=plannerService.insertPlanner(plannerDTO);
        assertEquals(Warning.SUCCESS,result);
        verify(plannerRepository).save(any(Planner.class));
    }

    @Test
    public void insertPlannerTestBad(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        user.setPlanners(new ArrayList<>());
        when(userRepository.findByUsername("oanamoisa")).thenReturn(java.util.Optional.of(user));
        List<Planner> allPlanners=new ArrayList<>();
        allPlanners.add(Planner.builder().id(1).name("Planner1").build());
        allPlanners.add(Planner.builder().id(2).name("Planner2").build());
        when(plannerRepository.findAllByUsersContains(user)).thenReturn(allPlanners);
        PlannerDTO plannerDTO=PlannerDTO.builder()
                .name("Planner2")
                .username("oanamoisa")
                .build();
        Warning result=plannerService.insertPlanner(plannerDTO);
        assertEquals(Warning.DUPLICATE,result);
        verify(plannerRepository,never()).save(any(Planner.class));
    }

    @Test
    public void inviteUser(){
        User user=new User(1,"Oana","Moisa","oanamoisa","oana", UserType.ROLE_ADMIN,"oana.miruna29@gmail.com");
        user.setPlanners(new ArrayList<>());
        User user2=new User(1,"Anca","Pop","ancapop","anca", UserType.ROLE_ADMIN,"ancapop@gmail.com");
        when(userRepository.findByUsername("oanamoisa")).thenReturn(java.util.Optional.of(user));
        List<String> users=new ArrayList<>();
        users.add("ancapop");
        InviteUserDTO inviteUserDTO=InviteUserDTO.builder().username("oanamoisa")
                .planner(PlannerDisplayDTO.builder().name("Planner1").users(users).build()).build();
        when(userRepository.findByUsername("ancapop")).thenReturn(java.util.Optional.of(user2));
        Planner planner=Planner.builder().id(1).name("Planner1").build();
        when(plannerRepository.findByName("Planner1")).thenReturn(java.util.Optional.of(planner));
        List<User> userList=new ArrayList<>();
        userList.add(user2);
        planner.setUsers(userList);
        plannerService.inviteUser(inviteUserDTO);
        verify(plannerRepository).save(any(Planner.class));
    }


}
