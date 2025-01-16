package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.dto.TradeDto;
import com.spentAnalysis.demo.dto.TradeResponseDto;
import com.spentAnalysis.demo.dto.UserTradeDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.entity.UserPortfolio;
import com.spentAnalysis.demo.entity.UserTrade;
import com.spentAnalysis.demo.enums.TradeType;
import com.spentAnalysis.demo.repository.StockRepository;
import com.spentAnalysis.demo.repository.UserPortfolioRepository;
import com.spentAnalysis.demo.repository.UserTradeRepository;
import com.spentAnalysis.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTradeService {
    @Autowired
    private final UserTradeRepository userTradeRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final StockRepository stockRepository;
    @Autowired
    private final UserPortfolioRepository userPortfolioRepository;

    @Transactional
    public List<UserTrade> getUserHolding(int userId){
        return userTradeRepository.findByUser_UserId(userId);
    }

    @Transactional

    public UserTrade addUserTrade(
            UserTradeDto userTradeDto,
            Long stockId,
            int userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        Stock stock = stockRepository.findByStockId(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found with ID: " + stockId));

        if(userTradeDto.getTradeType()!= TradeType.BUY && userTradeDto.getTradeType()!=TradeType.SELL){
            throw new IllegalArgumentException("Trade type is not valid: " + userTradeDto.getTradeType());
        }

        if(userTradeDto.getTradeType()==TradeType.BUY){
            // simply add to userPortfolio db
            UserPortfolio userPortfolio = new UserPortfolio(null,user,stock,userTradeDto.getQuantity(),stock.getStockPrice().getClosePrice(),userTradeDto.getQuantity(),null,null);
            userPortfolioRepository.save(userPortfolio);
        }
        else{
            //update open quantities in db
            List<UserPortfolio> userPortfolios = userPortfolioRepository.findByUserAndStockOrderByCreatedAtAsc(user, stock);

            int totalHoldingQuantity = userPortfolios.stream()
                    .mapToInt(UserPortfolio::getQuantity)
                    .sum();

            if (totalHoldingQuantity < userTradeDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient holdings for stock: " + stockId);
            }

            int remainingQuantityToSell = userTradeDto.getQuantity();

            for (UserPortfolio portfolio : userPortfolios) {
                if (remainingQuantityToSell <= 0) {
                    break;
                }

                int availableQuantity = portfolio.getOpenQuantity();

                if (availableQuantity <= remainingQuantityToSell) {
                    remainingQuantityToSell -= availableQuantity;
                    userPortfolioRepository.delete(portfolio);
                } else {
                    portfolio.setOpenQuantity(availableQuantity - remainingQuantityToSell);
                    userPortfolioRepository.save(portfolio);
                    remainingQuantityToSell = 0;
                }
            }
        }
        UserTrade userTrade = new UserTrade();
        userTrade.setUser(user);
        userTrade.setStock(stock);
        userTrade.setTradeType(userTradeDto.getTradeType());
        userTrade.setQuantity(userTradeDto.getQuantity());
        userTrade.setBuyPrice(stock.getStockPrice().getClosePrice());

        return userTradeRepository.save(userTrade);

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
