package com.spentAnalysis.demo.controller;

import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }
    @GetMapping("/getUserByUserId/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable("userId") int userId){

        Optional<User> user = userService.findUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
