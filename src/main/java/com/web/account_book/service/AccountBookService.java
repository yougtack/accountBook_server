package com.web.account_book.service;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.HopeGoal;
import com.web.account_book.model.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountBookService {
    List<AccountBookModel> getAccountBookList(String username, String start, String end);
    int save_account_book(AccountBook accountBook);
    int update_account_book(AccountBook accountBook);
    int delete_account_book(long ab_id);

    List<Income> findByUsername(String username);
    int save_income(Income income);
    int update_income(Income income);
    int delete_income(long income_id);

    IncomeThisMonth spending(String username);
    List<BudgetModel> getBudget(String username, String date);

    int save_budget(List<Budget> budgetList);
    int delete_budget(long budget_id);


    CumulativeModel getCumulative(String username);

    SpendingThisMonthModel spending_this_month(String username);

    BudgetThisMonth budget_this_month(String username);

    List<ReportModel> getReport(String username, String start, String end);

    List<ReportModel> getReportDetail(String username, String start, String end, String type);

    List<ReportModel> getReportSaving(String username, String start, String end);

    int save_hopeGoal(HopeGoal hopeGoal);
    int update_hopeGoal(HopeGoal hopeGoal);
    int delete_hopeGoal(long hope_id);
    List<HopeGoalModel> getHopeGoal(String username);

    List<AccountBookOnlyTypeModel> getOnlyType(String username);

    Page<HopeGoal> findBoardList(Pageable pageable);
}
