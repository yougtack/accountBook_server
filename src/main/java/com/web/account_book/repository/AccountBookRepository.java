package com.web.account_book.repository;

import com.web.account_book.model.AccountBookOnlyTypeModel;
import com.web.account_book.model.ReportModel;
import com.web.account_book.model.SpendingRankModel;
import com.web.account_book.model.AccountBookModel;
import com.web.account_book.model.entity.AccountBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {
    @Query(value = "SELECT * FROM account_book where username = ?1 and AB_write_date between ?2 and ?3", nativeQuery = true)
    List<AccountBook> findByDate(String username, String start, String end);

    @Query("SELECT max(a.AB_id) FROM AccountBook AS a")
    int findByIdentifier();

    @Query(value = "SELECT ifnull(sum(cash_cost), 0) FROM account_book where AB_write_date like ?1 and username = ?2", nativeQuery = true)
    int findByExpenditure_cash(String date, String username);

    @Query(value = "SELECT ifnull(sum(card_cost), 0) FROM account_book where AB_write_date like ?1 and username = ?2", nativeQuery = true)
    int findByExpenditure_card(String date, String username);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where username = ?1 and ab_write_date like ?2 and type like ?3", nativeQuery = true)
    int findByInsurance(String username, String this_month, String patten);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where username = ?1 and ab_write_date like ?2 and not type like ?3", nativeQuery = true)
    int findBySpending(String username, String this_month, String patten);

    @Query(value = "SELECT ifnull(sum(cash_cost), 0) AS cost FROM account_book where username = ?1", nativeQuery = true)
    int findBySumCash_money(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>예금' and username = ?1", nativeQuery = true)
    int findByDeposit(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>적금' and username = ?1", nativeQuery = true)
    int findBySave_money(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>펀드' and username = ?1", nativeQuery = true)
    int findByFund(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>보험' and username = ?1", nativeQuery = true)
    int findByInsurance_total(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>투자' and username = ?1", nativeQuery = true)
    int findByInvestment(String username);

    @Query(value = "SELECT ifnull(sum(cash_cost+card_cost), 0) AS cost FROM account_book where type like '저축/보험>기타' and username = ?1", nativeQuery = true)
    int findByEtc(String username);

    @Query(value = "select subString_index(type,'>',1) AS type, ifnull(sum(cash_cost+card_cost), 0) AS cost from account_book where username = ?1 and ab_write_date like ?2 and type not like '저축/보험>%'group by substring(type,1,2) limit 0, 4", nativeQuery = true)
    List<SpendingRankModel> findBySpendingRank(String username, String this_month);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account_book SET budget_id = ?1 WHERE username = ?2 and type like ?3", nativeQuery = true)
    int updateBudget_id(long budget_id, String username, String budget_type);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM account_book WHERE ab_id = ?1", nativeQuery = true)
    int deleteByAB_id(long ab_id);

    @Query(value = "SELECT" +
            "           SUBSTRING(type, 1 ,2) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           username = ?1" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       GROUP BY" +
            "       SUBSTRING(type, 1, 2)", nativeQuery = true)
    List<ReportModel> findByReport(String username, String start, String end);


    @Query(value = "SELECT" +
            "           SUBSTRING(type, 4) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           username = ?1" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       AND " +
            "           type like ?4" +
            "       GROUP BY" +
            "       SUBSTRING(type, 4, 5)", nativeQuery = true)
    List<ReportModel> findByReportDetail(String username, String start, String end, String type);

    @Query(value = "SELECT" +
            "           SUBSTRING(type, 7 ,9) AS type, SUM(card_cost+cash_cost) AS Total_cost," +
            "           SUM(card_cost) AS card_cost, SUM(cash_cost) AS cash_cost" +
            "       FROM " +
            "           account_book " +
            "       WHERE " +
            "           username = ?1" +
            "       AND " +
            "           type like '저축/보험>%'" +
            "       AND " +
            "           AB_write_date between ?2 and ?3" +
            "       GROUP BY" +
            "       SUBSTRING(type, 4, 5)", nativeQuery = true)
    List<ReportModel> findByReportSaving(String username, String start, String end);

    @Query(value = "SELECT SUBSTRING_INDEX(type, '>', 1) AS type FROM account_book WHERE username = ?1 and SUBSTRING_INDEX(type, '>', 1) not like '미분류' GROUP BY SUBSTRING_INDEX(type, '>', 1)", nativeQuery = true)
    List<AccountBookOnlyTypeModel> findByOnlyType(String username);
}