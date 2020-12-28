package com.web.account_book.repository;

import com.web.account_book.model.BudgetModel;
import com.web.account_book.model.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query(value = "SELECT " +
            "           IFNULL(SUBSTRING(a.type, 1, 2), b.budget_type) AS type, IFNULL(SUM(a.card_cost+a.cash_cost), 0) AS spending," +
            "           IFNULL(b.budget, 0) AS budget, IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost" +
            "       FROM" +
            "           account_book AS a " +
            "       RIGHT OUTER JOIN" +
            "           budget AS b " +
            "       ON SUBSTRING(a.type, 1, 2) = b.budget_type" +
            "       WHERE a.username = ?1 OR b.username = ?1" +
            "       AND b.insert_date LIKE ?2" +
            "       GROUP BY SUBSTRING(a.type, 1, 2)"+
            "       UNION" +
            "       SELECT" +
            "           IFNULL(SUBSTRING(a.type, 1, 2), b.budget_type) AS type, IFNULL(SUM(a.card_cost+a.cash_cost), 0) AS spending," +
            "           IFNULL(b.budget, 0) AS budget, IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost" +
            "       FROM" +
            "           account_book AS a" +
            "       LEFT OUTER JOIN" +
            "           budget AS b" +
            "       ON SUBSTRING(a.type, 1, 2) = b.budget_type" +
            "       WHERE a.username = ?1 OR b.username = ?1" +
            "       AND a.ab_write_date LIKE ?2" +
            "       GROUP BY SUBSTRING(a.type, 1, 2)" +
            "       ORDER BY 1"
            , nativeQuery = true)
    List<BudgetModel> findTotal_cost(String username, String month);

    @Query(value = "SELECT" +
            "           ifnull(sum(budget),0)" +
            "       FROM" +
            "           budget" +
            "       WHERE" +
            "           username = ?1" +
            "       AND " +
            "           insert_date like ?2",nativeQuery = true)
    int findByBudgetThisMonth(String username, String month);
}