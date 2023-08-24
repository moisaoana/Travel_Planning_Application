package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name="activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private int startHour;

    @Column(nullable=false)
    private int endHour;

    @ManyToOne
    @JoinColumn(name="day_id", nullable=false)
    private Day day;

    public Activity(){

    }
    public Activity(Integer id, String name, int startHour, int endHour, Day day) {
        this.id = id;
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
    }

    public Activity(String name, int startHour, int endHour, Day day) {
        this.name = name;
        this.startHour = startHour;
        this.endHour = endHour;
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
