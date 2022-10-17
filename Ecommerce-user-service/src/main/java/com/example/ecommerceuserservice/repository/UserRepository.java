package com.example.ecommerceuserservice.repository;

import com.example.ecommerceuserservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
