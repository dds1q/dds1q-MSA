package com.example.ecommerceuserservice.repository;

import com.example.ecommerceuserservice.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String username);
}
