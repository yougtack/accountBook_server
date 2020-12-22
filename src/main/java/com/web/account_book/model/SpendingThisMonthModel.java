package com.web.account_book.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpendingThisMonthModel {
    private int insurance;
    private int spending;

    private List<SpendingRankModel> spendingRanks;
}
