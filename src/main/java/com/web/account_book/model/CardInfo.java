package com.web.account_book.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long card_info_id;

    private long card_id;
    private String card_name;
    private String card_company;
    private boolean card_checkCard;
    private Date card_start_date;
    private Date card_end_date;
    private boolean card_use;

}
