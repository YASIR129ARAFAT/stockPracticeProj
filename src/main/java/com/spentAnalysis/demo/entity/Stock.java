package com.spentAnalysis.demo.entity;

import com.spentAnalysis.demo.dto.PricesDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String stockName;

    @Embedded
    @Column(name = "price",nullable = false, columnDefinition = "json")
    private PricesDto stockPrice;

    @CreationTimestamp
    @Column(name = "recordedAt", updatable = false, nullable = false)
    private LocalDate recordedAt;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
