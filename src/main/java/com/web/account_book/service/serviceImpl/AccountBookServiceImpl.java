package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.AccountBook;
import com.web.account_book.repository.AccountBookRepository;
import com.web.account_book.service.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountBookServiceImpl implements AccountBookService {

    @Autowired
    AccountBookRepository accountBookRepository;

    @Override
    public List<AccountBook> getAccountBookList(AccountBook accountBook){
        return accountBookRepository.findByUsernameLike(accountBook.getUsername());

    }

    @Override
    @Transactional
    public int save_account_book(AccountBook accountBook){
        try{
            AccountBook accountBookEntity = AccountBook.builder()
                    .username(accountBook.getUsername())
                    .AB_where_to_use(accountBook.getAB_where_to_use())
                    .card_id(accountBook.getCard_id())
                    .cash_id(accountBook.getCash_id())
                    .type(accountBook.getType())
                    .build();
            accountBookRepository.save(accountBookEntity);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
