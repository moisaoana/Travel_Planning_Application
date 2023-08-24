package com.example.demo.repository;

import com.example.demo.model.Country;
import com.example.demo.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Integer> {
    public List<Location> findAllByCreator_Username(String username);
    public Optional<Location> findByName(String name);
}
