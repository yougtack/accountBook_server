package com.web.account_book.repository;

import com.web.account_book.model.HopeGoalModel;
import com.web.account_book.model.entity.HopeGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HopeGoalRepository extends JpaRepository<HopeGoal, Long> {
    List<HopeGoal> findByUsername(String username);


    @Query(value = "SELECT" +
            "           h.hope_id, h.title, h.goal_cost, h.username, h.start_date," +
            "           h.end_date, h.create_date, IFNULL(h.references_type, 'empty') AS references_type," +
            "           IFNULL(SUM(card_cost+cash_cost), 0) AS sum_cost" +
            "       FROM" +
            "           account_book AS a" +
            "       RIGHT OUTER JOIN" +
            "           hope_goal AS h" +
            "       ON" +
            "           subString_index(a.type, '>',1) = h.references_type" +
            "       WHERE" +
            "           a.username = ?1" +
            "       OR " +
            "           a.username is null" +
            "       AND " +
            "           h.username = ?1" +
            "       AND" +
            "           a.ab_write_date between h.start_date and h.end_date" +
            "       OR" +
            "           a.ab_write_date is null" +
            "       GROUP BY subString_index(a.type, '>',1)",nativeQuery = true)
    List<HopeGoalModel> findByHopeGoal(String username);
}
