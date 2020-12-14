package com.web.account_book.repository;

import com.web.account_book.model.AccountBook;
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

}
