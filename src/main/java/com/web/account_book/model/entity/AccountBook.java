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

    private String AB_write_date;
    private String AB_where_to_use;

    private int cash_cost;
    private int card_cost;
    private String type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "username")
    private User user;

    private long budget_id;

    @Builder
    public AccountBook(long AB_id, String AB_write_date, String AB_where_to_use,
                       int cash_cost, int card_cost, String type, User user, long budget_id){
        this.AB_id = AB_id;
        this.AB_write_date = AB_write_date;
        this.AB_where_to_use = AB_where_to_use;
        this.cash_cost = cash_cost;
        this.card_cost = card_cost;
        this.type = type;
        this.user = user;
        this.budget_id = budget_id;
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