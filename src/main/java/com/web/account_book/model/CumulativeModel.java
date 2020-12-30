package com.web.account_book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CumulativeModel {
    private int total_cost; //자산합산
    private int sum_money; //현금자액
    private int deposit; //예금
    private int save_money; //적금
    private int fund; //펀드
    private int insurance; //보험
    private int investment; //투자
    private int etc; //기타
}
