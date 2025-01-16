package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.UserPortfolio;
import com.spentAnalysis.demo.service.UserPortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/userPortfolio")
@RequiredArgsConstructor
public class UserPortfolioController {
    @Autowired
    private final UserPortfolioService userPortfolioService;


    @GetMapping("/getUserPortfolio/{userId}")
    public ResponseEntity<?> getUserPortfolio(@PathVariable("userId") int userId) {

        List<UserPortfolio> userStocksHoldings = userPortfolioService.getUserPortfolio(userId);
        HashMap<Long, UserPortfolio> userHoldings = new HashMap<>();


        for (UserPortfolio holding : userStocksHoldings) {
            Long stockId = holding.getStock().getStockId();

            if (userHoldings.containsKey(stockId)) {
                UserPortfolio temp = userHoldings.get(stockId);
                temp.setOpenQuantity(temp.getOpenQuantity() + holding.getOpenQuantity());
                temp.setQuantity(temp.getQuantity() + holding.getQuantity());
                temp.setBuyPrice(
                        temp.getBuyPrice().multiply(new BigDecimal(temp.getQuantity()))
                                .add(holding.getBuyPrice().multiply(new BigDecimal(holding.getQuantity())))
                                .divide(new BigDecimal(temp.getQuantity() + holding.getQuantity()), BigDecimal.ROUND_HALF_UP)
                );
            } else {
                userHoldings.put(stockId, holding);
            }
        }


        List<HashMap<String, Object>> holdingsResponse = new ArrayList<>();
        BigDecimal totalPortfolioHolding = BigDecimal.ZERO;
        BigDecimal totalBuyPrice = BigDecimal.ZERO;
        BigDecimal totalGainLoss = BigDecimal.ZERO;

        for (UserPortfolio holding : userHoldings.values()) {
            BigDecimal currentPrice = holding.getStock().getStockPrice().getClosePrice(); // Assuming Stock entity has currentPrice
            BigDecimal buyPrice = holding.getBuyPrice();
            BigDecimal gainLoss = currentPrice.subtract(buyPrice).multiply(new BigDecimal(holding.getQuantity()));


            HashMap<String, Object> holdingDetails = new HashMap<>();
            holdingDetails.put("stockName", holding.getStock().getStockName());
            holdingDetails.put("stockId", holding.getStock().getStockId());
            holdingDetails.put("quantity", holding.getQuantity());
            holdingDetails.put("buyPrice", buyPrice);
            holdingDetails.put("currentPrice", currentPrice);
            holdingDetails.put("gainLoss", gainLoss);

            holdingsResponse.add(holdingDetails);

            totalPortfolioHolding = totalPortfolioHolding.add(currentPrice.multiply(new BigDecimal(holding.getQuantity())));
            totalBuyPrice = totalBuyPrice.add(buyPrice.multiply(new BigDecimal(holding.getQuantity())));
            totalGainLoss = totalGainLoss.add(gainLoss);
        }

        BigDecimal profitLossPercentage = totalBuyPrice.compareTo(BigDecimal.ZERO) > 0
                ? totalGainLoss.divide(totalBuyPrice, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))
                : BigDecimal.ZERO;


        HashMap<String, Object> response = new HashMap<>();
        response.put("holdings", holdingsResponse);
        response.put("totalPortfolioHolding", totalPortfolioHolding);
        response.put("totalBuyPrice", totalBuyPrice);
        response.put("totalGainLoss", totalGainLoss);
        response.put("profitLossPercentage", profitLossPercentage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
