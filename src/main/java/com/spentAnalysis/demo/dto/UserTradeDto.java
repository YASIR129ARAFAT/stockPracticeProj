package com.spentAnalysis.demo.dto;

import com.spentAnalysis.demo.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTradeDto {
    private TradeType tradeType;
    private int quantity;
    private BigDecimal buyPrice;
}
