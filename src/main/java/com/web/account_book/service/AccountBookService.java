package com.web.account_book.service;

import com.web.account_book.model.AccountBook;

import java.util.List;

public interface AccountBookService {
    List<AccountBook> getAccountBookList(AccountBook accountBook);
    int save_account_book(AccountBook accountBook);
}
