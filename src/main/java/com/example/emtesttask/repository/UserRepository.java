package com.example.emtesttask.repository;


import com.example.emtesttask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE u.username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
