package com.web.account_book.controller;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.AccountBook;
import com.web.account_book.model.entity.Budget;
import com.web.account_book.model.entity.Income;
import com.web.account_book.service.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/accountBook")
public class AccountBookController {

    @Autowired
    AccountBookService accountBookService;

    @GetMapping(value = "/{username}/{start}/{end}")
    public List<AccountBook> accountBootList(@PathVariable String username, @PathVariable String start, @PathVariable String end){
        return accountBookService.getAccountBookList(username, start, end);
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
    @GetMapping(value = "/spending/{username}")
    public IncomeThisMonth total(@PathVariable String username, HttpSession session){
        //인증
        //입력받은 username과 세션의 session.getAttribute("username")으로 비교해야함
        return accountBookService.spending(username);
    }

    //예산
    @GetMapping(value = "/budget/{username}/{date}")
    public List<BudgetModel> budget(@PathVariable String username, @PathVariable String date){
        return accountBookService.getBudget(username, date);
    }

    //예산 쓰기
    @PostMapping(value = "/budget")
    public int budgetInsert(@RequestBody Budget budget){
        return accountBookService.save_budget(budget);
    }

    //총 누적 자산(구현 중임)
    @GetMapping(value = "/cumulative/{username}")
    public CumulativeModel getCumulative(@PathVariable String username){
        return accountBookService.getCumulative(username);
    }

    //이달의 지춟 분석
    @GetMapping(value = "/spending_month/{username}")
    public SpendingThisMonthModel spending_this_month(@PathVariable String username){
        return accountBookService.spending_this_month(username);
    }

    //이달의 예산
    @GetMapping(value = "/budget_month/{username}")
    public BudgetThisMonth income_this_month(@PathVariable String username){
        return accountBookService.budget_this_month(username);
    }
}


