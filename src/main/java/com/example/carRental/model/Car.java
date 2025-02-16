package com.example.carRental.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String brand;
  private String model;
  private int year;
  private String status;

//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "car_id")
//  private List<Rental> rentals;

  public Car(String brand, String model, int year, String status) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.status = status;
  }

  public Car() {
  }

  public long getId() {
    return id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}

//CREATE TABLE cars (
//        id BIGINT PRIMARY KEY AUTO_INCREMENT,
//        brand VARCHAR(100) NOT NULL,
//model VARCHAR(100) NOT NULL,
//year INT NOT NULL,
//status VARCHAR(50) NOT NULL
//);
