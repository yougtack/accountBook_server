package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long card_info_id;

    private String card_name;
    private String card_company;
    private Boolean card_checkCard;
    private String card_start_date;
    private String card_end_date;
    private Boolean card_use;
    private String username;


    @Builder
    public CardInfo(long card_info_id, String card_name, String card_company, Boolean card_checkCard,
                    String card_start_date, String card_end_date, Boolean card_use, String username){
        this.card_info_id = card_info_id;
        this.card_name = card_name;
        this.card_company = card_company;
        this.card_checkCard = card_checkCard;
        this.card_start_date = card_start_date;
        this.card_end_date = card_end_date;
        this.card_use = card_use;
        this.username = username;
    }
}
