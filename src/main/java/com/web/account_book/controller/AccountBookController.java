package com.web.account_book.controller;

import com.web.account_book.model.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @GetMapping(value = "/spending")
    public IncomeThisMonth total(@RequestBody AccountBook accountBook){
        DateUtil dateUtil = new DateUtil();
        ExpenditureModel expenditureModel = new ExpenditureModel();
        IncomeModel incomeModel = new IncomeModel();
        IncomeThisMonth incomeThisMonth = new IncomeThisMonth();

        expenditureModel.setExpenditure_card(accountBookService.expenditure_this_month_card(accountBook, dateUtil.this_month));
        expenditureModel.setExpenditure_cash(accountBookService.expenditure_this_month_cash(accountBook, dateUtil.this_month));
        incomeThisMonth.setExpenditure_type(expenditureModel);

        incomeModel.setIncome(accountBookService.income_this_month(accountBook, dateUtil.this_month));
        incomeModel.setIncome_last_month(
                accountBookService.income_this_month(accountBook, dateUtil.last_month)-
                (accountBookService.expenditure_this_month_card(accountBook, dateUtil.last_month)+
                 accountBookService.expenditure_this_month_cash(accountBook, dateUtil.last_month))
        );
        incomeThisMonth.setIncome_type(incomeModel);


        incomeThisMonth.setIncomeThisMonth(incomeModel.getIncome()+incomeModel.getIncome_last_month());
        incomeThisMonth.setExpenditure(expenditureModel.getExpenditure_card()+expenditureModel.getExpenditure_cash());
        incomeThisMonth.setTotal(incomeThisMonth.getIncomeThisMonth()-incomeThisMonth.getExpenditure());

        return incomeThisMonth;
    }
}


