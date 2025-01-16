package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.dto.PricesDto;
import com.spentAnalysis.demo.dto.StockDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {
    @Autowired
    private final StockService stockService;

    @PostMapping("/addSingleStock")
    public ResponseEntity<?> addSingleStock(@RequestBody StockDto stockData){
        Stock newStock = stockService.addSingleStock(stockData);

        return new ResponseEntity<>(newStock,HttpStatus.OK);

    }
    @GetMapping("/getStock/{stockId}")
    public ResponseEntity<?> getSingleStock(@PathVariable("stockId") String stockId){
        Optional<Stock> stock = stockService.getSingleStock(stockId);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @PostMapping("/addStockFromCsv")
    public ResponseEntity<?> addUpdateStocks(@RequestBody MultipartFile csvFile){
        stockService.readCsvFile(csvFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
