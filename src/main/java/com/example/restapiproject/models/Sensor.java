package com.example.restapiproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Size(min = 3, max = 30, message = "Name length should be between 3 and 30 characters")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> listOfMeasurements;

    public Sensor() {
    }

    public Sensor(int id, String name) {
        this.id = id;
        this.name = name;
        listOfMeasurements = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setListOfMeasurements(List<Measurement> list){
        this.listOfMeasurements = list;
    }

    public void add(Measurement measurement){
        listOfMeasurements.add(measurement);
    }
}
