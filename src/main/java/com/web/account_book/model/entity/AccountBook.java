package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;


    @Builder
    public AccountBook(long AB_id, String username, String AB_write_date, String AB_where_to_use,
                       int cash_cost, int card_cost, String type, User user){
        this.AB_id = AB_id;
        this.username = username;
        this.AB_write_date = AB_write_date;
        this.AB_where_to_use = AB_where_to_use;
        this.cash_cost = cash_cost;
        this.card_cost = card_cost;
        this.type = type;
        this.user = user;
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