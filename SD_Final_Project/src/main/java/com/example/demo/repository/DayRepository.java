package com.example.demo.repository;

import com.example.demo.model.Day;
import com.example.demo.model.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository  extends JpaRepository<Day,Integer> {
    public List<Day> getAllByPlanner(Planner planner);
    public Optional<Day> findByDateAndPlanner(String name,Planner planner);
}
