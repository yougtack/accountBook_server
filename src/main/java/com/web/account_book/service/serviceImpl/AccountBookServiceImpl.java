package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.*;
import com.web.account_book.repository.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public List<AccountBook> getAccountBookList(AccountBookSelectModel accountBookSelectModel){
        return accountBookRepository.findByDate(accountBookSelectModel.getUsername(), accountBookSelectModel.getStart(), accountBookSelectModel.getEnd());
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

            if(accountBook.getCash_cost() > 0){

                Cash cashEntity = Cash.builder()
                        .AB_id(accountBookRepository.findByIdentifier())
                        .username(accountBook.getUsername())
                        .cash_cost(accountBook.getCash_cost())
                        .build();
                cashRepository.save(cashEntity);
            }else if(accountBook.getCard_cost() > 0){
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
                    .username(income.getUsername())
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

    @Override
    public int income_this_month(AccountBook accountBook, String date){
        return incomeRepository.findByIncome_date(date, accountBook.getUsername());
    }

    @Override
    public int expenditure_this_month_cash(AccountBook accountBook, String date){
        return accountBookRepository.findByExpenditure_cash(date, accountBook.getUsername());
    }
    @Override
    public int expenditure_this_month_card(AccountBook accountBook, String date){
        return accountBookRepository.findByExpenditure_card(date, accountBook.getUsername());
    }

    @Override
    public IncomeThisMonth spending(AccountBook accountBook){
        DateUtil dateUtil = new DateUtil();
        ExpenditureModel expenditureModel = new ExpenditureModel();
        IncomeModel incomeModel = new IncomeModel();
        IncomeThisMonth incomeThisMonth = new IncomeThisMonth();

        expenditureModel.setExpenditure_card(accountBookRepository.findByExpenditure_card(DateUtil.this_month, accountBook.getUsername()));
        expenditureModel.setExpenditure_cash(accountBookRepository.findByExpenditure_cash(DateUtil.this_month, accountBook.getUsername()));
        incomeThisMonth.setExpenditure_type(expenditureModel);

        incomeModel.setIncome(incomeRepository.findByIncome_date(DateUtil.this_month, accountBook.getUsername()));
        incomeModel.setIncome_last_month(
            incomeRepository.findByIncome_date(DateUtil.last_month, accountBook.getUsername())-
            (accountBookRepository.findByExpenditure_card(DateUtil.last_month, accountBook.getUsername())+
             accountBookRepository.findByExpenditure_cash(DateUtil.last_month, accountBook.getUsername()))
        );
        incomeThisMonth.setIncome_type(incomeModel);

        incomeThisMonth.setIncomeThisMonth(incomeModel.getIncome()+incomeModel.getIncome_last_month());
        incomeThisMonth.setExpenditure(expenditureModel.getExpenditure_card()+expenditureModel.getExpenditure_cash());
        incomeThisMonth.setTotal(incomeThisMonth.getIncomeThisMonth()-incomeThisMonth.getExpenditure());

        return incomeThisMonth;
    }
}
