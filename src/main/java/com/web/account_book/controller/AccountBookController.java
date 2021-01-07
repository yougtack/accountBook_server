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
    public List<AccountBookModel> accountBootList(@PathVariable String username, @PathVariable String start, @PathVariable String end){
        return accountBookService.getAccountBookList(username, start, end);
    }

    @PostMapping(value = "")
    public int insert_account_book(@RequestBody AccountBook accountBook){
        return accountBookService.save_account_book(accountBook);
    }

    @PutMapping(value = "")
    public int update_account_book(@RequestBody AccountBook accountBook){
        return accountBookService.update_account_book(accountBook);
    }

    @DeleteMapping(value = "/{ab_id}")
    public int delete_account_book(@PathVariable long ab_id){
        return accountBookService.delete_account_book(ab_id);
    }

    //수익
    @GetMapping(value = "/income/{username}")
    public List<Income> incomeList(@PathVariable String username){
        return accountBookService.findByUsername(username);
    }

    @PostMapping(value = "/income")
    public int insert_income(@RequestBody Income income){
        return accountBookService.save_income(income);
    }

    @PutMapping(value = "/income")
    public int update_income(@RequestBody Income income){
        return accountBookService.update_income(income);
    }

    @DeleteMapping(value = "/income/{income_id}")
    public int delete_income(@PathVariable long income_id){
        return accountBookService.delete_income(income_id);
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
    public int insert_budget(@RequestBody List<Budget> budgetList){
        return accountBookService.save_budget(budgetList);
    }

    @DeleteMapping(value = "/budget/{budget_id}")
    public int delete_budget(@PathVariable long budget_id){
        return accountBookService.delete_budget(budget_id);
    }

    //총 누적 자산
    @GetMapping(value = "/cumulative/{username}")
    public CumulativeModel getCumulative(@PathVariable String username){
        return accountBookService.getCumulative(username);
    }

    //이달의 지춟 분석
    @GetMapping(value = "/spending_month/{username}")
    public SpendingThisMonthModel spending_this_month(@PathVariable String username){
        return accountBookService.spending_this_month(username);
    }

    //이달의 예산(footer쪽에 있는거)
    @GetMapping(value = "/budget_month/{username}")
    public BudgetThisMonth income_this_month(@PathVariable String username){
        return accountBookService.budget_this_month(username);
    }

    //보고서
    @GetMapping(value = "/report/{username}/{start}/{end}")
    public List<ReportModel> report(@PathVariable String username, @PathVariable String start, @PathVariable String end){
        return accountBookService.getReport(username, start, end);
    }

    //보고서 상세보기
    @GetMapping(value = "/report_detail/{username}/{start}/{end}/{type}")
    public List<ReportModel> reportDetail(@PathVariable String username, @PathVariable String start, @PathVariable String end,
                                            @PathVariable String type){
        return accountBookService.getReportDetail(username, start, end, type);
    }

    //보고서 저축버튼 클릭시
    @GetMapping(value = "/report_saving/{username}/{start}/{end}")
    public List<ReportModel> repostSaving(@PathVariable String username, @PathVariable String start, @PathVariable String end){
        return accountBookService.getReportSaving(username, start, end);
    }
}


