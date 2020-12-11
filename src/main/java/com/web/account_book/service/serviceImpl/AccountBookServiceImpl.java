package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.AccountBook;
import com.web.account_book.model.Card;
import com.web.account_book.model.Cash;
import com.web.account_book.model.Income;
import com.web.account_book.repository.AccountBookRepository;
import com.web.account_book.repository.CardRepository;
import com.web.account_book.repository.CashRepository;
import com.web.account_book.repository.IncomeRepository;
import com.web.account_book.service.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountBookServiceImpl implements AccountBookService {

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    IncomeRepository incomeRepository;

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
                    .card_cost(accountBook.getCard_cost())
                    .cash_cost(accountBook.getCash_cost())
                    .type(accountBook.getType())
                    .build();
            accountBookRepository.save(accountBookEntity);

            //카드일때랑 현금일때 구분
            if(accountBook.getCash_cost() >= 0){

                Cash cashEntity = Cash.builder()
                        .AB_id(accountBookRepository.findByIdentifier())
                        .username(accountBook.getUsername())
                        .cash_cost(accountBook.getCash_cost())
                        .build();
                cashRepository.save(cashEntity);
            }else if(accountBook.getCard_cost() >= 0){
                Card cardEntity = Card.builder()
                        .AB_id(accountBookRepository.findByIdentifier())
                        .username(accountBook.getUsername())
                        .card_cost(accountBook.getCard_cost())
                        .build();

                cardRepository.save(cardEntity);
            }
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int save_income(Income income) {
        try{
            Income incomeEntity = Income.builder()
                    .income_where_to_get(income.getIncome_where_to_get())
                    .income_cost(income.getIncome_cost())
                    .income_type(income.getIncome_type())
                    .build();
            incomeRepository.save(incomeEntity);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
