package com.example.carRental.repository;

import com.example.carRental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  public boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
