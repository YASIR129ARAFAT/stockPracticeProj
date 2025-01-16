package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.dto.StockDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @GetMapping("/getStockByStockId/{stockId}")
    public ResponseEntity<?> getSingleStockById(@PathVariable("stockId") Long stockId){
        Optional<Stock> stock = stockService.getSingleStockByStockId(stockId);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }

    @GetMapping("/getStockByStockIsin/{isin}")
    public ResponseEntity<?> getSingleStockByIsin(@PathVariable("isin") String isin){
        Optional<Stock> stock = stockService.getSingleStockByStockIsin(isin);
        return new ResponseEntity<>(stock,HttpStatus.OK);
    }


//    @PostMapping("/addStockFromCsv")
//    public ResponseEntity<?> addUpdateStocks(@RequestBody MultipartFile csvFile){
//        stockService.readCsvFile(csvFile);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
