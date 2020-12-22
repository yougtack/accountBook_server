package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cash_id;

    private String username;
    private long AB_id;
    private long cash_cost;

    @Builder
    public Cash(long cash_id, String username, long AB_id, long cash_cost){
        this.cash_id = cash_id;
        this.username = username;
        this.AB_id = AB_id;
        this.cash_cost = cash_cost;
    }
}
