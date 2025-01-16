package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.dto.PricesDto;
import com.spentAnalysis.demo.dto.StockDto;
import com.spentAnalysis.demo.entity.Stock;
import com.spentAnalysis.demo.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StockService {
    @Autowired
    private final StockRepository stockRepository;

    @Transactional
    public Stock addSingleStock(StockDto stockData){
        PricesDto price = new PricesDto(stockData.getHighPrice(),stockData.getLowPrice(),stockData.getOpenPrice(),stockData.getClosePrice());
        Stock stock = new Stock(null,stockData.getStockIsin(),stockData.getStockName(),price,null,null,null);
        return stockRepository.save(stock);
    }
    @Transactional
    public Optional<Stock> getSingleStockByStockId(Long stockId){
        LocalDate today = LocalDate.now();
        return stockRepository.findByStockIdAndRecordedAt(stockId, today);
    }

    @Transactional
    public Optional<Stock> getSingleStockByStockIsin(String stockIsin){
        LocalDate today = LocalDate.now();
        return stockRepository.findByStockIsinAndRecordedAt(stockIsin, today);
    }

//    @Transactional
//    public void readCsvFile(MultipartFile csvFile) {
//
//        try {
////            ClassLoader classLoader = getClass().getClassLoader();
////            File inputFile = new File(classLoader.getResource("static/BhavCSV.csv").getFile());
//            BufferedReader input = new BufferedReader(new InputStreamReader(csvFile.getInputStream()));
//
//            String line = null;
//            boolean firstLine = true;
//
//            // Map to store column names and their respective indices
//            Map<String, Integer> columnIndices = new HashMap<>();
//            List<StockDto> results = new ArrayList<>();
//
//            // Read the first line (header) to get column indices
//            if ((line = input.readLine()) != null) {
//                String[] headerColumns = line.split(",");
//
//                // Map column names to their index in the header
//                for (int i = 0; i < headerColumns.length; i++) {
//                    columnIndices.put(headerColumns[i], i);
//                }
//            }
//
//            // Read the remaining lines and extract necessary data based on the column names
//            while ((line = input.readLine()) != null) {
//                String[] columns = line.split(",");
//
//                String isin = columns[columnIndices.get("ISIN")];  // ISIN
//                String stockName = columns[columnIndices.get("FinInstrmNm")];  // FinInstrmNm
//
//
//                BigDecimal openPrice = new BigDecimal(columns[columnIndices.get("OpnPric")]);  // OpenPrice
//                BigDecimal highPrice = new BigDecimal(columns[columnIndices.get("HghPric")]);  // HighPrice
//                BigDecimal lowPrice = new BigDecimal(columns[columnIndices.get("LwPric")]);  // LowPrice
//                BigDecimal closePrice = new BigDecimal(columns[columnIndices.get("ClsPric")]);  // ClosePrice
//
//                PricesDto price = new PricesDto(openPrice,closePrice,highPrice,lowPrice);
//
//                Optional<Stock> existingStockOpt = stockRepository.findByStockIdAndRecordedAt(isin,LocalDate.now());
//
//                if (existingStockOpt.isPresent()) {
//                    // If the stock exists, update if any values have changed
//                    Stock existingStock = existingStockOpt.get();
//
//                    if (!existingStock.getStockName().equals(stockName) ||
//                            !existingStock.getStockPrice().equals(price)) {
//
//                        existingStock.setStockName(stockName);
//                        existingStock.setStockPrice(price);
//                        stockRepository.save(existingStock); // Update the stock
//                    }
//                } else {
//                    // If the stock does not exist, add it
//                    Stock newStock = new Stock(null, isin, stockName, price,null,null,null);
//                    stockRepository.save(newStock); // Save the new stock
//                }
//
//            }
//
//            input.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
