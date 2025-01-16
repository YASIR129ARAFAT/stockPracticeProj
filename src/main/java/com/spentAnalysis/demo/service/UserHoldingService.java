package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.dto.TradeDto;
import com.spentAnalysis.demo.dto.TradeResponseDto;
import com.spentAnalysis.demo.dto.UserTradeDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.entity.UserTrade;
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
    public List<UserTrade> getUserHolding(int userId){
        return userHoldingRepository.findByUser_UserId(userId);
    }

    @Transactional
    public UserTrade addUserHolding(
                UserTradeDto userTradeDto,
                String stockId,
                int userId
            ){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));

        UserTrade userTrade = new UserTrade();
        userTrade.setUser(user);
        userTrade.setStock(stock);
        userTrade.setTradeType(userTradeDto.getTradeType());
        userTrade.setQuantity(userTradeDto.getQuantity());
        userTrade.setBuyPrice(userTradeDto.getBuyPrice());

        return userHoldingRepository.save(userTrade);


    }

    @Transactional
    public TradeResponseDto getAllUserHolding(int userId){
        List<UserTrade> userTradeList = getUserHolding(userId);
        List<TradeDto> tradeDtos = new ArrayList<>();

        BigDecimal totalPortfolioHolding = BigDecimal.ZERO;
        BigDecimal totalBuyPrice = BigDecimal.ZERO;
        BigDecimal totalCurrentPrice = BigDecimal.ZERO;
        for(UserTrade userTrade : userTradeList){
            TradeDto tradeDto = new TradeDto();

            tradeDto.setBuyPrice(userTrade.getBuyPrice());
            tradeDto.setQuantity(userTrade.getQuantity());
            tradeDto.setStockId(userTrade.getStock().getStockId());
            tradeDto.setStockName(userTrade.getStock().getStockName());
            tradeDto.setCurrentPrice(userTrade.getStock().getStockPrice().getClosePrice());
            tradeDto.setGainLoss((userTrade.getStock().getStockPrice().getClosePrice()).subtract(userTrade.getBuyPrice()));

            tradeDtos.add(tradeDto);

            totalBuyPrice = totalBuyPrice.add(userTrade.getBuyPrice());
            totalPortfolioHolding = totalPortfolioHolding.add(BigDecimal.valueOf(userTrade.getQuantity()));
            totalCurrentPrice = totalCurrentPrice.add(userTrade.getStock().getStockPrice().getClosePrice());
        }

        TradeResponseDto tradeResponseDto = new TradeResponseDto();
        tradeResponseDto.setTradeDto(tradeDtos);
        tradeResponseDto.setTotalPortfolioHolding(totalPortfolioHolding);
        tradeResponseDto.setTotalBuyPrice(totalBuyPrice);

        BigDecimal totalProfitLossPercentage = totalCurrentPrice.subtract(totalBuyPrice).divide(totalBuyPrice,3).multiply(BigDecimal.valueOf(100));
        tradeResponseDto.setTotalProfitLossPercentage(totalProfitLossPercentage);

        return tradeResponseDto;
    }
}
