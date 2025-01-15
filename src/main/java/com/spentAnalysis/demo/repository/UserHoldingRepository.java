package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.UserHolding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHoldingRepository extends JpaRepository<UserHolding,Long> {
    List<UserHolding> findByUser_UserId(int userId);
}
