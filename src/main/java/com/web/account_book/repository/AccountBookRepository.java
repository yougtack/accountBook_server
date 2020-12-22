package com.web.account_book.repository;

import com.web.account_book.model.CumulativeModel;
import com.web.account_book.model.SpendingRankModel;
import com.web.account_book.model.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long>{
    @Query(value = "SELECT * FROM account_book where username= ?1 and AB_write_date between ?2 and ?3", nativeQuery = true)
    List<AccountBook> findByDate(String username, String start, String end);

    @Query("SELECT max(a.AB_id) FROM AccountBook AS a")
    int findByIdentifier();

    @Query(value = "SELECT ifnull(sum(cash_cost), 0) FROM account_book where AB_write_date like ?1 and username = ?2", nativeQuery = true)
    int findByExpenditure_cash(String date, String username);

    @Query(value = "SELECT ifnull(sum(card_cost), 0) FROM account_book where AB_write_date like ?1 and username = ?2", nativeQuery = true)
    int findByExpenditure_card(String date, String username);

    @Query(value = "SELECT ", nativeQuery = true)
    CumulativeModel findByCumulative(String username);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where username = ?1 and ab_write_date like ?2 and type like ?3", nativeQuery = true)
    int findByInsurance(String username, String this_month, String patten);

    @Query(value = "select ifnull(sum(cash_cost+card_cost), 0) from account_book where username = ?1 and ab_write_date like ?2 and not type like ?3", nativeQuery = true)
    int findBySpending(String username, String this_month, String patten);

    @Query(value = "select subString(type,1,2) AS type, ifnull(sum(cash_cost+card_cost), 0) AS cost from account_book where username = ?1 and ab_write_date like ?2 group by substring(type,1,2) limit 0, 4",nativeQuery = true)
    List<SpendingRankModel> findBySpendingRank(String username, String this_month);

}
