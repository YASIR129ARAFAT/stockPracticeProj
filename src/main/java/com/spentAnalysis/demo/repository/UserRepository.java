package com.spentAnalysis.demo.repository;

import com.spentAnalysis.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
