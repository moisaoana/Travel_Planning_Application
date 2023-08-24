package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name="planner")
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique= true, nullable=false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="planner")
    private List<Day> days;

    @ManyToMany(mappedBy = "planners")
    private List<User> users;

    public Planner(){

    }

    public Planner(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Planner( String name) {
        this.name = name;
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

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
