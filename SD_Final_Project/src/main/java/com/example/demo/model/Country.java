package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name="country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique= true,nullable=false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy="country")
    private List<Location> locations;

    public Country(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(String name) {
        this.name = name;
    }

    public Country(){

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
}
