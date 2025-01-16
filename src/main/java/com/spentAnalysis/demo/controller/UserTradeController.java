package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.dto.UserTradeDto;
import com.spentAnalysis.demo.entity.UserTrade;

import com.spentAnalysis.demo.service.UserTradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userTrade")
@RequiredArgsConstructor
public class UserTradeController {
    @Autowired
    private final UserTradeService userTradeService;

    @GetMapping("/getUserTrade/{userId}")
    public ResponseEntity<?> getUserHolding(@PathVariable("userId") int userId){
        List<UserTrade> userTradeList = userTradeService.getUserHolding(userId);

        return new ResponseEntity<>(userTradeList, HttpStatus.OK);
    }

    @PostMapping("/addUserTrade/{stockId}/{userId}")
    public ResponseEntity<?> addUserHolding(
            @RequestBody UserTradeDto userTradeDto,
            @PathVariable("stockId") Long stockId,
            @PathVariable("userId") int userId
    ){
        return new ResponseEntity<>(userTradeService.addUserTrade(userTradeDto,stockId,userId),HttpStatus.OK);
    }

//    @GetMapping("/getUserPortfolio/{userId}")
//    public ResponseEntity<?> getUserPortfolio(@PathVariable("userId") int userId){
//        return new ResponseEntity<>(userHoldingService.getAllUserHolding(userId),HttpStatus.OK);
//    }

}
