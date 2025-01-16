package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.entity.UserPortfolio;
import com.spentAnalysis.demo.repository.UserPortfolioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPortfolioService {
    @Autowired
    private final UserPortfolioRepository userPortfolioRepository;

    @Transactional
    public List<UserPortfolio> getUserPortfolio(int userId){
        return userPortfolioRepository.findByUser_UserId(userId);
    }

}
