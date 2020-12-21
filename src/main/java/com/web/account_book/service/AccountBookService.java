package com.web.account_book.service;

import com.web.account_book.model.AccountBook;
import com.web.account_book.model.AccountBookSelectModel;
import com.web.account_book.model.Income;
import com.web.account_book.model.IncomeThisMonth;

import java.util.List;

public interface AccountBookService {
    List<AccountBook> getAccountBookList(AccountBookSelectModel accountBookSelectModel);
    int save_account_book(AccountBook accountBook);
    int save_income(Income income);
    int income_this_month(AccountBook accountBook, String this_month);
    int expenditure_this_month_cash(AccountBook accountBook, String this_month);
    int expenditure_this_month_card(AccountBook accountBook, String this_month);

    IncomeThisMonth spending(AccountBook accountBook);
}
