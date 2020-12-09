package com.web.account_book.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AB_id;

    private String username;

    @CreationTimestamp
    private Timestamp AB_write_date;
    private String AB_where_to_use;
    private int cash_id;
    private int card_id;
    private String type;

    @Builder
    public AccountBook(int AB_id, String username, Timestamp AB_write_date, String AB_where_to_use,
                       int cash_id, int card_id, String type){
        this.AB_id = AB_id;
        this.username = username;
        this.AB_write_date = AB_write_date;
        this.AB_where_to_use = AB_where_to_use;
        this.cash_id = cash_id;
        this.card_id = card_id;
        this.type = type;
    }
}