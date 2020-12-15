package com.web.account_book.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncomeThisMonth {
    private int incomeThisMonth;
    private IncomeModel income_type;
    private int expenditure;
    private ExpenditureModel expenditure_type;
    private int total;
}
