package com.example.carRental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "rentals")
public class Rental {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "car_id")
  private Car car;

  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private LocalDate rentalStart;
  private LocalDate rentalEnd;
  private BigDecimal price;

  public Rental(Car car, User user, LocalDate rentalStart, LocalDate rentalEnd, BigDecimal price) {
    this.car = car;
    this.user = user;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
  }

  public Rental() {
  }

  public long getId() {
    return id;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDate getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDate rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDate getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDate rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}

//CREATE TABLE rentals (
//        id BIGINT PRIMARY KEY AUTO_INCREMENT,
//        user_id BIGINT NOT NULL,
//        car_id BIGINT NOT NULL,
//        FOREIGN KEY (user_id) REFERENCES users(id),
//        FOREIGN KEY (car_id) REFERENCES cars(id),
//        rental_start DATE NOT NULL,
//        rental_end DATE NOT NULL,
//        price DECIMAL(10,2) NOT NULL
//);