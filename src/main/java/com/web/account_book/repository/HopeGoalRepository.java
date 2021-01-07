package com.web.account_book.repository;

import com.web.account_book.model.entity.HopeGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopeGoalRepository extends JpaRepository<HopeGoal, Long> {
    List<HopeGoal> findByUsername(String username);
}
