package com.springboot.jwttask.repository;

import com.springboot.jwttask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUserId(String userId);
}
