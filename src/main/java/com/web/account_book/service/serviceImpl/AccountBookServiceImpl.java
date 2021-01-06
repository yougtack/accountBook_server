package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.*;
import com.web.account_book.repository.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class AccountBookServiceImpl implements AccountBookService {
    DateUtil dateUtil = new DateUtil();

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    CashRepository cashRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardInfoRepository cardInfoRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    BudgetRepository budgetRepository;

    //컬렉션 스트림을 이용해서 dto로 부어라(프론트와 서버를 연결)
    @Override
    public List<AccountBookModel> getAccountBookList(String username, String start, String end){
        User userEntity = userRepository.findByUsername(username); //username을 통해 userEntitiy를 가져옴(유저 식별키를 얻기위함)
        List<AccountBook> list_temp = accountBookRepository.findByDate(userEntity.getId(), start, end);

        List<AccountBookModel> list = new ArrayList<>();
        Stream<AccountBook> stream = list_temp.stream();
        stream.forEach(s -> {
            AccountBookModel accountBookModel = new AccountBookModel();
            UserModel userModel = new UserModel();

            accountBookModel.setAb_id(s.getAB_id());
            accountBookModel.setAb_where_to_use(s.getAB_where_to_use());
            accountBookModel.setAb_write_date(s.getAB_write_date());
            accountBookModel.setCard_cost(s.getCard_cost());
            accountBookModel.setCash_cost(s.getCash_cost());
            accountBookModel.setType(s.getType());
            accountBookModel.setBudget_id(s.getBudget_id());

            userModel.setId(s.getUser().getId());
            userModel.setUsername(s.getUser().getUsername());
            userModel.setEmail(s.getUser().getEmail());

            accountBookModel.setUser(userModel);
            list.add(accountBookModel);
        });

        return list;
    }

    @Override
    @Transactional
    public int save_account_book(AccountBook accountBook){
        User userEntity = userRepository.findByUsername(accountBook.getUser().getUsername());
        AccountBook accountBookEntity = AccountBook.builder()
                .AB_where_to_use(accountBook.getAB_where_to_use())
                .AB_write_date(accountBook.getAB_write_date())
                .card_cost(accountBook.getCard_cost())
                .cash_cost(accountBook.getCash_cost())
                .type(accountBook.getType())
                .user(userEntity)
                .build();
        return account_book_util(accountBookEntity, accountBook);
    }

    @Override
    @Transactional
    public int update_account_book(AccountBook accountBook){
        User userEntity = userRepository.findByUsername(accountBook.getUser().getUsername());
        AccountBook accountBookEntity = AccountBook.builder()
                .AB_id(accountBook.getAB_id())
                .AB_where_to_use(accountBook.getAB_where_to_use())
                .AB_write_date(accountBook.getAB_write_date())
                .card_cost(accountBook.getCard_cost())
                .cash_cost(accountBook.getCash_cost())
                .type(accountBook.getType())
                .user(userEntity)
                .build();

        account_book_cost_util(accountBook);
        return account_book_util(accountBookEntity, accountBook);
    }

    private int account_book_util(AccountBook accountBookEntity, AccountBook accountBook){
        try{
            accountBookRepository.save(accountBookEntity);

            if(accountBook.getCash_cost() > 0){
                Cash cashEntity = Cash.builder()
                        .AB_id(accountBookRepository.findByIdentifier())
                        .username(accountBook.getUser().getUsername())
                        .cash_cost(accountBook.getCash_cost())
                        .build();
                cashRepository.save(cashEntity);
            }else if(accountBook.getCard_cost() > 0){
                Card cardEntity = Card.builder()
                        .AB_id(accountBookRepository.findByIdentifier())
                        .username(accountBook.getUser().getUsername())
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

    private void account_book_cost_util(AccountBook accountBook){
        if(accountBook.getCard_cost() == 0){
            cardRepository.deleteByAB_id(accountBook.getAB_id());
        }else if(accountBook.getCash_cost() == 0){
            cashRepository.deleteByAB_id(accountBook.getAB_id());
        }
    }

    @Transactional
    @Override
    public int delete_account_book(long ab_id){
        int result = 0;

        result += accountBookRepository.deleteByAB_id(ab_id);
        result += cardRepository.deleteByAB_id(ab_id);
        result += cashRepository.deleteByAB_id(ab_id);

        return result;
    }

    @Override
    public List<Income> findByUsername(String username){
        return incomeRepository.findByUsername(username);
    }

    @Override
    public int save_income(Income income) {
        try{
            Income incomeEntity = Income.builder()
                    .username(income.getUsername())
                    .income_date(income.getIncome_date())
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
    public int update_income(Income income){
        try{
            Income incomeEntity = Income.builder()
                    .income_id(income.getIncome_id())
                    .username(income.getUsername())
                    .income_date(income.getIncome_date())
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

    @Transactional
    @Override
    public int delete_income(long income_id){
        return incomeRepository.deleteByIncome_id(income_id);
    }

    @Override
    public IncomeThisMonth spending(String username){
        User userEntity = userRepository.findByUsername(username);
        ExpenditureModel expenditureModel = new ExpenditureModel();
        IncomeModel incomeModel = new IncomeModel();
        IncomeThisMonth incomeThisMonth = new IncomeThisMonth();

        expenditureModel.setExpenditure_card(accountBookRepository.findByExpenditure_card(DateUtil.this_month, userEntity.getId()));
        expenditureModel.setExpenditure_cash(accountBookRepository.findByExpenditure_cash(DateUtil.this_month, userEntity.getId()));
        incomeThisMonth.setExpenditure_type(expenditureModel);

        incomeModel.setIncome(incomeRepository.findByIncome_date(DateUtil.this_month, username));
        incomeModel.setIncome_last_month(
            incomeRepository.findByIncome_date(DateUtil.last_month, username)-
            (accountBookRepository.findByExpenditure_card(DateUtil.last_month, userEntity.getId())+
             accountBookRepository.findByExpenditure_cash(DateUtil.last_month, userEntity.getId()))
        );
        incomeThisMonth.setIncome_type(incomeModel);

        incomeThisMonth.setIncomeThisMonth(incomeModel.getIncome()+incomeModel.getIncome_last_month());
        incomeThisMonth.setExpenditure(expenditureModel.getExpenditure_card()+expenditureModel.getExpenditure_cash());
        incomeThisMonth.setTotal(incomeThisMonth.getIncomeThisMonth()-incomeThisMonth.getExpenditure());

        return incomeThisMonth;
    }

    @Override
    public List<BudgetModel> getBudget(String username, String date) {
        User userEntity = userRepository.findByUsername(username);
        return budgetRepository.findTotal_cost(username, date+"%", userEntity.getId());
    }

    @Transactional
    @Override
    public int save_budget(Budget budget) {
        User userEntity = userRepository.findByUsername(budget.getUsername());
        try{
            Budget budgetEntity = Budget.builder()
                    .username(budget.getUsername())
                    .insert_date(budget.getInsert_date())
                    .budget(budget.getBudget())
                    .budget_type(budget.getBudget_type())
                    .build();
            if(budgetRepository.findByUsernameLikeAndBudget_typeLike(budget.getUsername(), budget.getBudget_type()) != null){
                budgetRepository.update(budget.getUsername(), budget.getInsert_date(), budget.getBudget(), budget.getBudget_type());
            }else{
                budgetRepository.save(budgetEntity);
                budgetEntity = budgetRepository.findByUsernameLikeAndBudget_typeLike(budget.getUsername(), budget.getBudget_type());

                accountBookRepository.updateBudget_id(budgetEntity.getBudget_id(), userEntity.getId(), budgetEntity.getBudget_type()+"%");
            }
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
            return 1;
        }
    }

    @Transactional
    @Override
    public int delete_budget(long budget_id){
        return budgetRepository.deleteByBudget_id(budget_id);
    }

    @Override
    public CumulativeModel getCumulative(String username) {
        User userEntity = userRepository.findByUsername(username);
        CumulativeModel cumulativeModel = new CumulativeModel();

        cumulativeModel.setSum_money(incomeRepository.findByIncome(username) - accountBookRepository.findBySumCash_money(userEntity.getId()));
        cumulativeModel.setDeposit(accountBookRepository.findByDeposit(userEntity.getId()));
        cumulativeModel.setSave_money(accountBookRepository.findBySave_money(userEntity.getId()));
        cumulativeModel.setFund(accountBookRepository.findByFund(userEntity.getId()));
        cumulativeModel.setInsurance(accountBookRepository.findByInsurance_total(userEntity.getId()));
        cumulativeModel.setInvestment(accountBookRepository.findByInvestment(userEntity.getId()));
        cumulativeModel.setEtc(accountBookRepository.findByEtc(userEntity.getId()));
        cumulativeModel.setTotal_cost(cumulativeModel.getSum_money()+
                cumulativeModel.getDeposit()+
                cumulativeModel.getSave_money()+
                cumulativeModel.getFund()+
                cumulativeModel.getInsurance()+
                cumulativeModel.getInvestment()+
                cumulativeModel.getEtc());
        return cumulativeModel;
    }

    @Override
    public SpendingThisMonthModel spending_this_month(String username){
        User userEntity = userRepository.findByUsername(username);

        System.out.println(DateUtil.this_month);
        SpendingThisMonthModel spendingThisMonthModel = new SpendingThisMonthModel();
        spendingThisMonthModel.setInsurance(accountBookRepository.findByInsurance(userEntity.getId(), DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpending(accountBookRepository.findBySpending(userEntity.getId(), DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpendingRanks(accountBookRepository.findBySpendingRank(userEntity.getId(), DateUtil.this_month));

        return spendingThisMonthModel;
    }

    @Override
    public BudgetThisMonth budget_this_month(String username) {
        User userEntity = userRepository.findByUsername(username);
        BudgetThisMonth budgetThisMonth = new BudgetThisMonth();
        budgetThisMonth.setSpending_this_month(
                accountBookRepository.findByExpenditure_cash(DateUtil.this_month, userEntity.getId())+
                    accountBookRepository.findByExpenditure_card(DateUtil.this_month, userEntity.getId())
        );
        budgetThisMonth.setBudget_this_month(budgetRepository.findByBudgetThisMonth(username, DateUtil.this_month));

        return budgetThisMonth;
    }

    @Override
    public List<ReportModel> getReport(String username, String start, String end){
        User userEntity = userRepository.findByUsername(username);

        return accountBookRepository.findByReport(userEntity.getId(), start, end);
    }

    @Override
    public List<ReportModel> getReportDetail(String username, String start, String end, String type){
        User userEntity = userRepository.findByUsername(username);
        return accountBookRepository.findByReportDetail(userEntity.getId(), start, end, type+"%");
    }

    @Override
    public List<ReportModel> getReportSaving(String username, String start, String end){
        User userEntity = userRepository.findByUsername(username);
        return accountBookRepository.findByReportSaving(userEntity.getId(), start, end);
    }

    @Override
    public int saveCardInfo(CardInfo cardInfo){
        try{
            CardInfo cardInfoEntity;
            if(cardInfo.getCard_checkCard() == true){
                cardInfoEntity = CardInfo.builder()
                    .card_name(cardInfo.getCard_name())
                    .card_company(cardInfo.getCard_company())
                    .card_checkCard(cardInfo.getCard_checkCard())
                    .card_use(cardInfo.getCard_use())
                    .username(cardInfo.getUsername())
                    .build();
            }else{
                cardInfoEntity = CardInfo.builder()
                    .card_name(cardInfo.getCard_name())
                    .card_company(cardInfo.getCard_company())
                    .card_checkCard(cardInfo.getCard_checkCard())
                    .card_start_date(cardInfo.getCard_start_date())
                    .card_end_date(cardInfo.getCard_end_date())
                    .card_use(cardInfo.getCard_use())
                    .username(cardInfo.getUsername())
                    .build();
            }
            cardInfoRepository.save(cardInfoEntity);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<CardInfo> getCard_info(String username){
        return cardInfoRepository.findByUsername(username);
    }
}

