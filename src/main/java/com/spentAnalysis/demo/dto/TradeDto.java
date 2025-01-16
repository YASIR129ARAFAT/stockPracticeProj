package com.spentAnalysis.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeDto {
    private String stockName;
    private Long stockId;
    private int quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice;
    private BigDecimal gainLoss;
}
