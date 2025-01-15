package com.spentAnalysis.demo.entity;

import com.spentAnalysis.demo.dto.PricesDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @Column(name="stockId",nullable = false,unique = true)
    private String stockId;

    @Column(name = "name",nullable = false)
    private String name;

    @Embedded
    @Column(name = "price",nullable = false, columnDefinition = "json")
    private PricesDto price;
}
