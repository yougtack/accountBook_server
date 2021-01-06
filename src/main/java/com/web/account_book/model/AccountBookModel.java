package com.web.account_book.model;

import com.web.account_book.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AccountBookModel {
    private long ab_id;
    private String ab_write_date;
    private String ab_where_to_use;
    private int cash_cost;
    private int card_cost;
    private String type;
    private UserModel user;
    private int budget_id;

}
