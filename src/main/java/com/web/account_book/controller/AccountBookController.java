package com.web.account_book.controller;

import com.web.account_book.model.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
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

    @GetMapping(value = "/spending") //이거 서비스로 옮겨야함
    public IncomeThisMonth total(@RequestBody AccountBook accountBook){
        return accountBookService.spending(accountBook);
    }
    @PostMapping(value = "/budget")
    public void budget(){

    }
}


