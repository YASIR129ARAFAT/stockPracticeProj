package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,String> {
//    Optional<Stock> findByStockId(String stockId);
        Optional<Stock> findByStockIdAndRecordedAt(String stockId, LocalDate recordedDate);

}
