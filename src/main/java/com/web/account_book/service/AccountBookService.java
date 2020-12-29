package com.web.account_book.service;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.Income;

import java.util.List;

public interface AccountBookService {
    List<AccountBook> getAccountBookList(String username, String start, String end);
    int save_account_book(AccountBook accountBook);
    int update_account_book(AccountBook accountBook);
    int delete_account_book(long ab_id);

    int save_income(Income income);
    int update_income(Income income);
    int delete_income(long income_id);

    IncomeThisMonth spending(String username);
    List<BudgetModel> getBudget(String username, String date);

    int save_budget(Budget budget);
    int update_budget(Budget budget);
    int delete_budget(long budget_id);


    CumulativeModel getCumulative(String username);

    SpendingThisMonthModel spending_this_month(String username);

    BudgetThisMonth budget_this_month(String username);
}
