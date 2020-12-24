package com.web.account_book.service;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.Income;

import java.util.List;

public interface AccountBookService {
    List<AccountBook> getAccountBookList(String username, String start, String end);
    int save_account_book(AccountBook accountBook);
    int save_income(Income income);

    IncomeThisMonth spending(String username);
    List<BudgetModel> getBudget(String username, String date);
    int save_budget(Budget budget);

    CumulativeModel getCumulative(String username);

    SpendingThisMonthModel spending_this_month(String username);
}
