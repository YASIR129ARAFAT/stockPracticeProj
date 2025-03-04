package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUserId(int userId);
}
