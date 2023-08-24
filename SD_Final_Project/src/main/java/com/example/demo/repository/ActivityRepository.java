package com.example.demo.repository;

import com.example.demo.model.Activity;
import com.example.demo.model.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findAllByDay(Day day);
}
