package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.UserTrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHoldingRepository extends JpaRepository<UserTrade,Long> {
    List<UserTrade> findByUser_UserId(int userId);
}
