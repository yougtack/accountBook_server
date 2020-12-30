package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AB_id;

    private String username;

    private String AB_write_date;
    private String AB_where_to_use;
    private int cash_cost;
    private int card_cost;
    private String type;

    @Builder
    public AccountBook(long AB_id, String username, String AB_write_date, String AB_where_to_use,
                       int cash_cost, int card_cost, String type){
        this.AB_id = AB_id;
        this.username = username;
        this.AB_write_date = AB_write_date;
        this.AB_where_to_use = AB_where_to_use;
        this.cash_cost = cash_cost;
        this.card_cost = card_cost;
        this.type = type;
    }

    public void update(String AB_write_date, String AB_where_to_use,
                       int cash_cost, int card_cost, String type){
        this.AB_write_date = AB_write_date;
        this.AB_where_to_use = AB_where_to_use;
        this.cash_cost = cash_cost;
        this.card_cost = card_cost;
        this.type = type;
    }
}