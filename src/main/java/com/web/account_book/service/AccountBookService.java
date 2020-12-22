package com.web.account_book.service;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.Income;

import java.util.List;

public interface AccountBookService {
    List<AccountBook> getAccountBookList(AccountBookSelectModel accountBookSelectModel);
    int save_account_book(AccountBook accountBook);
    int save_income(Income income);

    IncomeThisMonth spending(AccountBook accountBook);
    List<BudgetModel> getBudget(BudgetFindModel budgetFindModel);
    int save_budget(Budget budget);

    CumulativeModel getCumulative(AccountBook accountBook);

    SpendingThisMonthModel spending_this_month(AccountBook accountBook);
}
