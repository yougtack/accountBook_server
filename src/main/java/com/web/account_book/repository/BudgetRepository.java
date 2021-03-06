package com.web.account_book.repository;

import com.web.account_book.model.BudgetLookBackModel;
import com.web.account_book.model.BudgetModel;
import com.web.account_book.model.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query(value = "SELECT "+
            "           IFNULL(SUBSTRING_INDEX(a.type, '>', 1), b.budget_type) AS type, IFNULL(SUM(a.card_cost+a.cash_cost), 0) AS spending,"+
            "           IFNULL(b.budget, 0) AS budget, IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost"+
            "       FROM " +
            "           account_book AS a " +
            "       LEFT OUTER JOIN  " +
            "           budget AS b " +
            "       ON a.budget_id = b.budget_id" +
            "       WHERE " +
            "           a.username = ?1 " +
            "       AND " +
            "           a.type not like '저축/보험>%'" +
            "       AND" +
            "           a.ab_write_date like ?2"+
            "       GROUP BY " +
            "           subString_index(a.type, '>', 1)" +
            "       UNION" +
            "       SELECT" +
            "           IFNULL(SUBSTRING_INDEX(a.type, '>', 1), b.budget_type) AS type, IFNULL(SUM(a.card_cost+a.cash_cost), 0) AS spending,"+
            "           IFNULL(b.budget, 0) AS budget, IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost" +
            "       FROM " +
            "           account_book AS a " +
            "       RIGHT OUTER JOIN  " +
            "           budget AS b " +
            "       ON a.budget_id = b.budget_id" +
            "       WHERE " +
            "           b.username = ?1 " +
            "       AND " +
            "           a.username is null" +
            "       AND" +
            "           b.insert_date like ?2"+
            "       GROUP BY " +
            "           subString_index(b.budget_type, '>', 1)" +
            "       ORDER BY 1 DESC"
            , nativeQuery = true)
    List<BudgetModel> findTotal_cost(String username, String month);

    @Query(value = "SELECT" +
            "           ifnull(sum(budget),0)" +
            "       FROM" +
            "           budget" +
            "       WHERE" +
            "           username = ?1" +
            "       AND " +
            "           insert_date like ?2", nativeQuery = true)
    int findByBudgetThisMonth(String username, String month);

    @Modifying
    @Query(value = "DELETE FROM budget WHERE budget_id = ?1", nativeQuery = true)
    int deleteByBudget_id(long budget_id);

    @Query(value = "SELECT * FROM budget WHERE username = ?1 AND budget_type like ?2", nativeQuery = true)
    Budget findByUsernameLikeAndBudget_typeLike(String username, String budget_type);

    @Query(value = "SELECT * FROM budget WHERE username = ?1 AND insert_date like SubString(?2, 1, 7) AND budget_type like SUBSTRING_INDEX(?3, '>', 1)", nativeQuery = true)
    Budget findByUsernameAndBudget_type(String username, String ab_write_date, String budget_type);


    @Modifying
    @Query(value = "UPDATE budget SET insert_date = ?2, budget = ?3 WHERE username = ?1 AND budget_type = ?4", nativeQuery = true)
    int update(String username, String insert_date, long budget, String budget_type);

    @Query(value = "SELECT" +
            "           IFNULL(SUBSTRING_INDEX(a.type, '>', 1), b.budget_type) AS type," +
            "           IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost," +
            "           IFNULL(SUBSTRING(a.ab_write_date, 1, 7), SUBSTRING(b.insert_date, 1, 7)) AS date" +
            "       FROM" +
            "           account_book AS a" +
            "       LEFT OUTER JOIN" +
            "           budget AS b" +
            "       ON " +
            "           a.budget_id = b.budget_id" +
            "       WHERE" +
            "           a.username = ?1" +
            "       AND" +
            "           a.type not like '저축/보험>%'" +
            "       AND " +
            "           a.ab_write_date >= ?2" +
            "       AND" +
            "           a.ab_write_date <= ?3" +
            "       GROUP BY" +
            "           subString(a.ab_write_date, 1, 7), subString_index(a.type, '>', 1)" +
            "       UNION" +
            "       SELECT" +
            "           IFNULL(SUBSTRING_INDEX(a.type, '>', 1), b.budget_type) AS type," +
            "           IFNULL(b.budget - IFNULL(SUM(card_cost+cash_cost), 0), -IFNULL(SUM(card_cost+cash_cost), 0)) AS total_cost," +
            "           IFNULL(SUBSTRING(a.ab_write_date, 1, 7), SUBSTRING(b.insert_date, 1, 7)) AS date" +
            "       FROM" +
            "           account_book AS a" +
            "       RIGHT OUTER JOIN" +
            "           budget AS b" +
            "       ON " +
            "           a.budget_id = b.budget_id" +
            "       WHERE" +
            "           b.username = ?1" +
            "       AND " +
            "           a.username is null" +
            "       AND" +
            "           b.insert_date >= ?2" +
            "       AND" +
            "           b.insert_date <= ?3" +
            "       GROUP BY" +
            "           subString(b.insert_date, 1, 7),subString_index(b.budget_type, '>', 1)" +
            "       ORDER BY 3 ASC, 1 DESC", nativeQuery = true)
    List<BudgetLookBackModel> findByBudgetLookBack(String username, String start, String end);
}