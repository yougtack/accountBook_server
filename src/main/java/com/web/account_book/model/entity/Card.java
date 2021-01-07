package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long card_id;

    private String username;
    private long AB_id;
    private int card_cost;

    @Builder
    public Card(long card_id, String username, long AB_id, int card_cost){
        this.card_id = card_id;
        this.username = username;
        this.AB_id = AB_id;
        this.card_cost = card_cost;
    }
}