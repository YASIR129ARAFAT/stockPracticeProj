package com.spentAnalysis.demo.entity;

import com.spentAnalysis.demo.enums.TradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "user_trade")
public class UserTrade {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long tradeId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId",
            foreignKey = @ForeignKey(name = "FK_Trade_User"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "stockId", referencedColumnName = "stockId",
            foreignKey = @ForeignKey(name = "FK_Trade_Stock"))
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradeType tradeType;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal buyPrice;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;
}
