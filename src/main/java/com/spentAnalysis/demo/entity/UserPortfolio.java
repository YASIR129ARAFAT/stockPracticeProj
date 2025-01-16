package com.spentAnalysis.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "user_portfolio")
@AllArgsConstructor
@NoArgsConstructor
public class UserPortfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long holdingId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "FK_Holding_User"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "stockId", referencedColumnName = "stockId",
            foreignKey = @ForeignKey(name = "FK_Holding_Stock"))
    private Stock stock;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal BuyPrice;

    @Column(nullable = false)
    private int openQuantity;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;



}
