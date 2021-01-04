package com.web.account_book.repository;

import com.web.account_book.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query(value = "SELECT ifnull(sum(income_cost), 0) FROM income where income_date like ?1 and username = ?2", nativeQuery = true)
    int findByIncome_date(String date, String username);

    @Query(value = "SELECT ifnull(sum(income_cost), 0) FROM income where username = ?1", nativeQuery = true)
    int findByIncome(String username);

    @Modifying
    @Query(value = "DELETE FROM income WHERE income_id = ?1", nativeQuery = true)
    int deleteByIncome_id(long income_id);

    List<Income> findByUsername(String username);
}
