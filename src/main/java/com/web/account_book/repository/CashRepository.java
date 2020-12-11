package com.web.account_book.repository;

import com.web.account_book.model.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {
}
