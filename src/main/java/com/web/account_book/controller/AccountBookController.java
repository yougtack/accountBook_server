package com.web.account_book.controller;

import com.web.account_book.model.AccountBook;
import com.web.account_book.model.Income;
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
    public List<AccountBook> accountBootList(@RequestBody AccountBook accountBook){
        return accountBookService.getAccountBookList(accountBook);
    }

    @PostMapping(value = "")
    public int insert(@RequestBody AccountBook accountBook){
        return accountBookService.save_account_book(accountBook);
    }

    @PostMapping(value = "/income")
    public int income(@RequestBody Income income){
        return accountBookService.save_income(income);
    }

}
