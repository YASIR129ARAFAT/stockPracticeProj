package com.spentAnalysis.demo.dto;

import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHoldingDto {
    private TradeType tradeType;
    private int quantity;
    private BigDecimal buyPrice;
}
