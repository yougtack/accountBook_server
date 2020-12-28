package com.web.account_book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CumulativeModel {
    private int total_cost;
    private int sum_money;
    private int deposit; //예금
    private int save_money; //적금
    private int fund; //펀드
    private int insurance; //보험
    private int investment; //투자
    private int etc; //기타
}
