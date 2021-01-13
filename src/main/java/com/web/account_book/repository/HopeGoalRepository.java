package com.web.account_book.repository;

import com.web.account_book.model.HopeGoalModel;
import com.web.account_book.model.entity.HopeGoal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface HopeGoalRepository extends JpaRepository<HopeGoal, Long> {
    List<HopeGoal> findByUsername(String username);


    @Query(value = "SELECT " +
            "           h.hope_id, h.title, h.goal_cost, h.username, h.start_date," +
            "           h.end_date, h.create_date, IFNULL(h.references_type, 'empty') AS references_type," +
            "           IFNULL(SUM(card_cost+cash_cost), 0) AS sum_cost" +
            "       FROM" +
            "           account_book as a" +
            "       RIGHT OUTER JOIN" +
            "           hope_goal AS h" +
            "       ON subString_index(a.type, '>', 1) = h.references_type" +
            "       WHERE" +
            "           h.username = ?1" +
            "       GROUP BY subString_index(a.type, '>', 1)",nativeQuery = true)
    List<HopeGoalModel> findByHopeGoal(String username);

    @Query(value = "SELECT * FROM hope_goal WHERE username = ?2 limit ?1", nativeQuery = true)
    Page<HopeGoal> findAllByUsername(Pageable pageable, String username);
}
