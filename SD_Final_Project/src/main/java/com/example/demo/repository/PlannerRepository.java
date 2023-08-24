package com.example.demo.repository;

import com.example.demo.model.Planner;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner,Integer> {
    List<Planner> findAllByUsersContains(User user);
    Optional<Planner> findByNameAndUsersIn(String name, Collection<List<User>> users);
    Optional<Planner> findByName(String name);
}
