package com.web.account_book.repository;

import com.web.account_book.model.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM cash WHERE ab_id = ?1",nativeQuery = true)
    int deleteByAB_id(long ab_id);
}
