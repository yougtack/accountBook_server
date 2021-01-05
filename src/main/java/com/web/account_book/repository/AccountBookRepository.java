package com.web.account_book.repository;

import com.web.account_book.model.ReportModel;
import com.web.account_book.model.SpendingRankModel;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long>{
    @Query(value = "SELECT * FROM account_book where id = ?1 and AB_write_date between ?2 and ?3", nativeQuery = true)
    List<AccountBook> findByDate(int user_id, String start, String end);

    @Query("SELECT max(a.AB_id) FROM AccountBook AS a")
    int findByIdentifier();

    @Query(value = "SELECT ifnull(sum(cash_cost), 0) FROM account_book where AB_write_date like ?1 and id = ?2", nativeQuery = true)
    int findByExpenditure_cash(String date, int user_id);

    @Query(value = "SELECT ifnull(sum(card_cost), 0) FROM account_book where AB_write_date like ?1 and id = ?2", nativeQuery = true)
    int findByExpenditure_card(String date, int user_id);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where id = ?1 and ab_write_date like ?2 and type like ?3", nativeQuery = true)
    int findByInsurance(int user_id, String this_month, String patten);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where id = ?1 and ab_write_date like ?2 and not type like ?3", nativeQuery = true)
    int findBySpending(int user_id, String this_month, String patten);

    @Query(value = "SELECT ifnull(sum(cash_cost), 0) AS cost FROM account_book where id = ?1", nativeQuery = true)
    int findBySumCash_money(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>예금' and id = ?1", nativeQuery = true)
    int findByDeposit(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>적금' and id = ?1", nativeQuery = true)
    int findBySave_money(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>펀드' and id = ?1", nativeQuery = true)
    int findByFund(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>보험' and id = ?1", nativeQuery = true)
    int findByInsurance_total(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>투자' and id = ?1", nativeQuery = true)
    int findByInvestment(int user_id);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>기타' and id = ?1", nativeQuery = true)
    int findByEtc(int user_id);

    @Query(value = "select subString_index(type,'>',1) AS type, ifnull(sum(cash_cost+card_cost), 0) AS cost from account_book where id = ?1 and ab_write_date like ?2 and type not like '저축/보험>%'group by substring(type,1,2) limit 0, 4",nativeQuery = true)
    List<SpendingRankModel> findBySpendingRank(int user_id, String this_month);

    @Modifying
    @Query(value = "UPDATE account_book SET budget_id = ?1 WHERE id = ?2 and type like ?3",nativeQuery = true)
    int updateBudget_id(long budget_id, int user_id, String budget_type);

    @Modifying
    @Query(value = "DELETE FROM account_book WHERE ab_id = ?1",nativeQuery = true)
    int deleteByAB_id(long ab_id);

    @Query(value = "SELECT" +
            "           SUBSTRING(type, 1 ,2) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           id = ?1" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       GROUP BY" +
            "       SUBSTRING(type, 1, 2)", nativeQuery = true)
    List<ReportModel> findByReport(int user_id, String start, String end);


    @Query(value = "SELECT" +
            "           SUBSTRING(type, 4) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           id = ?1" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       AND " +
            "           type like ?4" +
            "       GROUP BY" +
            "       SUBSTRING(type, 4, 5)", nativeQuery = true)
    List<ReportModel> findByReportDetail(int user_id, String start, String end, String type);

    @Query(value = "SELECT" +
            "           SUBSTRING(type, 7 ,9) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           id = ?1" +
            "       AND " +
            "           type like '저축/보험>%'" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       GROUP BY" +
            "       SUBSTRING(type, 4, 5)", nativeQuery = true)
    List<ReportModel> findByReportSaving(int user_id, String start, String end);
}
