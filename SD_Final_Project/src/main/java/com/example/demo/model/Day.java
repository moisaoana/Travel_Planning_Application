package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name="day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique= true, nullable=false)
    private String date;

    @ManyToOne
    @JoinColumn(name="planner_id", nullable=false)
    private Planner planner;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="day")
    private List<Activity> activities;

    public Day(){

    }

    public Day(Integer id, String date, Planner planner) {
        this.id = id;
        this.date = date;
        this.planner = planner;
    }

    public Day(String date, Planner planner) {
        this.date = date;
        this.planner = planner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Planner getPlanner() {
        return planner;
    }

    public void setPlanner(Planner planner) {
        this.planner = planner;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
