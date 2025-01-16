package com.spentAnalysis.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeResponseDto {
    private List<TradeDto> tradeDto;
    private BigDecimal totalPortfolioHolding;
    private BigDecimal totalBuyPrice;
    private BigDecimal totalProfitLossPercentage;
}
