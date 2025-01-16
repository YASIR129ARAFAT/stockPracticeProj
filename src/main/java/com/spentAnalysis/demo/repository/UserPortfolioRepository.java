package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.entity.UserPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPortfolioRepository extends JpaRepository<UserPortfolio,Long> {
    List<UserPortfolio> findByUserAndStockOrderByCreatedAtAsc(User user, Stock stock);
    List<UserPortfolio> findByUser_UserId(int userId);
}
