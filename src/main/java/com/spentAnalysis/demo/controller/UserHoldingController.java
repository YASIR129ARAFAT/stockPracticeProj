package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.dto.UserHoldingDto;
import com.spentAnalysis.demo.entity.UserHolding;
import com.spentAnalysis.demo.service.UserHoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userHolding")
@RequiredArgsConstructor
public class UserHoldingController {
    @Autowired
    private final UserHoldingService userHoldingService;

    @GetMapping("/getUserHolding/{userId}")
    public ResponseEntity<?> getUserHolding(@PathVariable("userId") int userId){
        List<UserHolding> userHoldingList = userHoldingService.getUserHolding(userId);

        return new ResponseEntity<>(userHoldingList, HttpStatus.OK);
    }

    @PostMapping("/addUserHolding/{stockId}/{userId}")
    public ResponseEntity<?> addUserHolding(
            @RequestBody UserHoldingDto userHoldingDto,
            @PathVariable("stockId") String stockId,
            @PathVariable("userId") int userId
    ){

        return new ResponseEntity<>(userHoldingService.addUserHolding(userHoldingDto,stockId,userId),HttpStatus.OK);
    }

    @GetMapping("/getUserPortfolio/{userId}")
    public ResponseEntity<?> getUserPortfolio(@PathVariable("userId") int userId){
        return new ResponseEntity<>(userHoldingService.getAllUserHolding(userId),HttpStatus.OK);
    }

}
