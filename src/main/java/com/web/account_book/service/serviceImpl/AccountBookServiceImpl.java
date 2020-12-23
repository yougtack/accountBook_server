package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.*;
import com.web.account_book.repository.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AccountBookServiceImpl implements AccountBookService {
    DateUtil dateUtil = new DateUtil();

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public List<AccountBook> getAccountBookList(String username, String start, String end){
        return accountBookRepository.findByDate(username, start, end);
    }

    @Override
    @Transactional
    public int save_account_book(AccountBook accountBook){
        try{
            AccountBook accountBookEntity = AccountBook.builder()
                    .username(accountBook.getUsername())
                    .AB_where_to_use(accountBook.getAB_where_to_use())
                    .AB_write_date(accountBook.getAB_write_date())
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
    public IncomeThisMonth spending(String username){
        ExpenditureModel expenditureModel = new ExpenditureModel();
        IncomeModel incomeModel = new IncomeModel();
        IncomeThisMonth incomeThisMonth = new IncomeThisMonth();

        expenditureModel.setExpenditure_card(accountBookRepository.findByExpenditure_card(DateUtil.this_month, username));
        expenditureModel.setExpenditure_cash(accountBookRepository.findByExpenditure_cash(DateUtil.this_month, username));
        incomeThisMonth.setExpenditure_type(expenditureModel);

        incomeModel.setIncome(incomeRepository.findByIncome_date(DateUtil.this_month, username));
        incomeModel.setIncome_last_month(
            incomeRepository.findByIncome_date(DateUtil.last_month, username)-
            (accountBookRepository.findByExpenditure_card(DateUtil.last_month, username)+
             accountBookRepository.findByExpenditure_cash(DateUtil.last_month, username))
        );
        incomeThisMonth.setIncome_type(incomeModel);

        incomeThisMonth.setIncomeThisMonth(incomeModel.getIncome()+incomeModel.getIncome_last_month());
        incomeThisMonth.setExpenditure(expenditureModel.getExpenditure_card()+expenditureModel.getExpenditure_cash());
        incomeThisMonth.setTotal(incomeThisMonth.getIncomeThisMonth()-incomeThisMonth.getExpenditure());

        return incomeThisMonth;
    }

    @Override
    public List<BudgetModel> getBudget(String username, String date) {
        return budgetRepository.findTotal_cost(username, date+"%");
    }

    @Override
    public int save_budget(Budget budget) {
        try{
            Budget budgetEntity = Budget.builder()
                    .username(budget.getUsername())
                    .budget(budget.getBudget())
                    .budget_type(budget.getBudget_type())
                    .build();
            budgetRepository.save(budgetEntity);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
            return 1;
        }
    }

    @Override //구현중
    public CumulativeModel getCumulative(String username) {
        accountBookRepository.findByCumulative(username);
        return null;
    }

    @Override
    public SpendingThisMonthModel spending_this_month(String username){
        SpendingThisMonthModel spendingThisMonthModel = new SpendingThisMonthModel();
        spendingThisMonthModel.setInsurance(accountBookRepository.findByInsurance(username, DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpending(accountBookRepository.findBySpending(username, DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpendingRanks(accountBookRepository.findBySpendingRank(username, DateUtil.this_month));

        return spendingThisMonthModel;
    }
}
