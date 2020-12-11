package com.web.account_book.repository;

import com.web.account_book.model.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Long>{
    List<AccountBook> findByUsernameLike(String username);

    @Query("SELECT max(a.AB_id) FROM AccountBook AS a ")
    int findByIdentifier();

}
