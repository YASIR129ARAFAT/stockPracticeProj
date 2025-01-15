package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.dto.HoldingDto;
import com.spentAnalysis.demo.dto.HoldingResponseDto;
import com.spentAnalysis.demo.dto.UserHoldingDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.entity.UserHolding;
import com.spentAnalysis.demo.repository.StockRepository;
import com.spentAnalysis.demo.repository.UserHoldingRepository;
import com.spentAnalysis.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHoldingService {
    @Autowired
    private final UserHoldingRepository userHoldingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final StockRepository stockRepository;

    @Transactional
    public List<UserHolding> getUserHolding(int userId){
        return userHoldingRepository.findByUser_UserId(userId);
    }

    @Transactional
    public UserHolding addUserHolding(
                UserHoldingDto userHoldingDto,
                String stockId,
                int userId
            ){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));

        UserHolding userHolding = new UserHolding();
        userHolding.setUser(user);
        userHolding.setStock(stock);
        userHolding.setTradeType(userHoldingDto.getTradeType());
        userHolding.setQuantity(userHoldingDto.getQuantity());
        userHolding.setBuyPrice(userHoldingDto.getBuyPrice());

        return userHoldingRepository.save(userHolding);


    }

    @Transactional
    public HoldingResponseDto getAllUserHolding(int userId){
        List<UserHolding> userHoldingList = getUserHolding(userId);
        List<HoldingDto> holdingDtos = new ArrayList<>();

        BigDecimal totalPortfolioHolding = BigDecimal.ZERO;
        BigDecimal totalBuyPrice = BigDecimal.ZERO;
        BigDecimal totalCurrentPrice = BigDecimal.ZERO;
        for(UserHolding userHolding:userHoldingList){
            HoldingDto holdingDto = new HoldingDto();

            holdingDto.setBuyPrice(userHolding.getBuyPrice());
            holdingDto.setQuantity(userHolding.getQuantity());
            holdingDto.setStockId(userHolding.getStock().getStockId());
            holdingDto.setStockName(userHolding.getStock().getName());
            holdingDto.setCurrentPrice(userHolding.getStock().getPrice().getClose());
            holdingDto.setGainLoss((userHolding.getStock().getPrice().getClose()).subtract(userHolding.getBuyPrice()));

            holdingDtos.add(holdingDto);

            totalBuyPrice = totalBuyPrice.add(userHolding.getBuyPrice());
            totalPortfolioHolding = totalPortfolioHolding.add(BigDecimal.valueOf(userHolding.getQuantity()));
            totalCurrentPrice = totalCurrentPrice.add(userHolding.getStock().getPrice().getClose());
        }

        HoldingResponseDto holdingResponseDto = new HoldingResponseDto();
        holdingResponseDto.setHoldingDto(holdingDtos);
        holdingResponseDto.setTotalPortfolioHolding(totalPortfolioHolding);
        holdingResponseDto.setTotalBuyPrice(totalBuyPrice);

        BigDecimal totalProfitLossPercentage = totalCurrentPrice.subtract(totalBuyPrice).divide(totalBuyPrice,3).multiply(BigDecimal.valueOf(100));
        holdingResponseDto.setTotalProfitLossPercentage(totalProfitLossPercentage);

        return holdingResponseDto;
    }
}
