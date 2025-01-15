package com.spentAnalysis.demo.dto;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldingResponseDto {
    private List<HoldingDto> holdingDto;
    private BigDecimal totalPortfolioHolding;
    private BigDecimal totalBuyPrice;
    private BigDecimal totalProfitLossPercentage;
}
