package com.spentAnalysis.demo.dto;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PricesDto {
    private BigDecimal open;
    private BigDecimal close;
    private BigDecimal high;
    private BigDecimal low;

}
