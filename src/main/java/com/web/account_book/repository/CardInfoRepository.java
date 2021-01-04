package com.web.account_book.repository;

import com.web.account_book.model.entity.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    List<CardInfo> findByUsername(String username);

    @Query(value = "SELECT * FROM card_info where card_info_id = ?1", nativeQuery = true)
    CardInfo findByCardInfo(long id);
}
