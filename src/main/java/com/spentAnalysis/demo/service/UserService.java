package com.spentAnalysis.demo.service;

import com.spentAnalysis.demo.entity.User;
import com.spentAnalysis.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository  userRepository;

    @Transactional
    public User addUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findUserById(int userId){
        return userRepository.findByUserId(userId);
    }




}
