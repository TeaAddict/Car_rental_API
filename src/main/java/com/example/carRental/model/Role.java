package com.example.carRental.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

  public Role(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Role() {
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return name;
  }
}

//CREATE TABLE roles (
//        id BIGINT PRIMARY KEY AUTO_INCREMENT,
//        name VARCHAR(50) NOT NULL UNIQUE
//);