package com.spentAnalysis.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockDto {
    private Long stockId;
    private String stockIsin;
    private String stockName;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
}
