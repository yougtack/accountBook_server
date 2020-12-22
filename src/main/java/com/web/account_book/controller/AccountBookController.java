package com.web.account_book.controller;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.Income;
import com.web.account_book.service.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/accountBook")
public class AccountBookController {

    @Autowired
    AccountBookService accountBookService;

    @GetMapping(value = "")
    public List<AccountBook> accountBootList(@RequestBody AccountBookSelectModel accountBookSelectModel){
        return accountBookService.getAccountBookList(accountBookSelectModel);
    }

    @PostMapping(value = "")
    public int insert(@RequestBody AccountBook accountBook){
        return accountBookService.save_account_book(accountBook);
    }

    @PostMapping(value = "/income")
    public int income(@RequestBody Income income){
        return accountBookService.save_income(income);
    }

    //지출
    @GetMapping(value = "/spending")
    public IncomeThisMonth total(@RequestBody AccountBook accountBook){
        return accountBookService.spending(accountBook);
    }

    //예산
    @GetMapping(value = "/budget")
    public List<BudgetModel> budget(@RequestBody BudgetFindModel budgetFindModel){
        return accountBookService.getBudget(budgetFindModel);
    }

    @PostMapping(value = "/budget")
    public int budgetInsert(@RequestBody Budget budget){
        return accountBookService.save_budget(budget);
    }

    //누적 자산
    @GetMapping(value = "/cumulative")
    public CumulativeModel getCumulative(@RequestBody AccountBook accountBook){
        return accountBookService.getCumulative(accountBook);
    }

    @GetMapping(value = "/spending_month")
    public SpendingThisMonthModel spending_this_month(@RequestBody AccountBook accountBook){
        return accountBookService.spending_this_month(accountBook);
    }

}


