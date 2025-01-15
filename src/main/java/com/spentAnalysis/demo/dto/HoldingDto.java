package com.spentAnalysis.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoldingDto {
    private String stockName;
    private String stockId;
    private int quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice;
    private BigDecimal gainLoss;
}
