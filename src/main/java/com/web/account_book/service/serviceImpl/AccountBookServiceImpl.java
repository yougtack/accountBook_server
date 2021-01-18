package com.web.account_book.service.serviceImpl;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.*;
import com.web.account_book.repository.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    IncomeRepository incomeRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    HopeGoalRepository hopeGoalRepository;

    //컬렉션 스트림을 이용해서 dto로 부어라(프론트와 서버를 연결)
    @Override
    public List<AccountBookModel> getAccountBookList(String username, String start, String end){
        List<AccountBook> list_tmp = accountBookRepository.findByDate(username, start, end);
        Stream<AccountBook> stream = list_tmp.stream();

        List<AccountBookModel> list = new ArrayList<>();

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
                .budget_id(budget_util(accountBook.getUser().getUsername(), accountBook.getType()))
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
                .budget_id(budget_util(accountBook.getUser().getUsername(), accountBook.getType()))
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

    public long budget_util(String username, String type){
        if(budgetRepository.findByUsernameAndBudget_type(username, type) != null){
            return budgetRepository.findByUsernameAndBudget_type(username, type).getBudget_id();
        }else{
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

    @Transactional
    @Override
    public int save_budget(List<Budget> budgetList) {
        try{
            for(Budget budget: budgetList){
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

                    accountBookRepository.updateBudget_id(budgetEntity.getBudget_id(), budget.getUsername(), budgetEntity.getBudget_type()+"%");
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
            return 1;
        }
    }

    @Override
    public List<BudgetLookBackModel> budget_look_back(String username, String start, String end){
        return budgetRepository.findByBudgetLookBack(username, start, end);
    }

    @Transactional
    @Override
    public int delete_budget(long budget_id){
        return budgetRepository.deleteByBudget_id(budget_id);
    }

    @Override
    public CumulativeModel getCumulative(String username) {
        CumulativeModel cumulativeModel = new CumulativeModel();

        cumulativeModel.setSum_money(incomeRepository.findByIncome(username) - accountBookRepository.findBySumCash_money(username));
        cumulativeModel.setDeposit(accountBookRepository.findByDeposit(username));
        cumulativeModel.setSave_money(accountBookRepository.findBySave_money(username));
        cumulativeModel.setFund(accountBookRepository.findByFund(username));
        cumulativeModel.setInsurance(accountBookRepository.findByInsurance_total(username));
        cumulativeModel.setInvestment(accountBookRepository.findByInvestment(username));
        cumulativeModel.setEtc(accountBookRepository.findByEtc(username));
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
        SpendingThisMonthModel spendingThisMonthModel = new SpendingThisMonthModel();
        spendingThisMonthModel.setInsurance(accountBookRepository.findByInsurance(username, DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpending(accountBookRepository.findBySpending(username, DateUtil.this_month, "저축/보험>%"));
        spendingThisMonthModel.setSpendingRanks(accountBookRepository.findBySpendingRank(username, DateUtil.this_month));

        return spendingThisMonthModel;
    }

    @Override
    public BudgetThisMonth budget_this_month(String username) {
        BudgetThisMonth budgetThisMonth = new BudgetThisMonth();
        budgetThisMonth.setSpending_this_month(
                accountBookRepository.findByExpenditure_cash(DateUtil.this_month, username)+
                    accountBookRepository.findByExpenditure_card(DateUtil.this_month, username)
        );
        budgetThisMonth.setBudget_this_month(budgetRepository.findByBudgetThisMonth(username, DateUtil.this_month));

        return budgetThisMonth;
    }

    @Override
    public List<ReportModel> getReport(String username, String start, String end){
        return accountBookRepository.findByReport(username, start, end);
    }

    @Override
    public List<ReportModel> getReportDetail(String username, String start, String end, String type){
        return accountBookRepository.findByReportDetail(username, start, end, type+"%");
    }

    @Override
    public List<ReportModel> getReportSaving(String username, String start, String end){
        return accountBookRepository.findByReportSaving(username, start, end);
    }

    @Override
    @Transactional
    public int save_hopeGoal(HopeGoal hopeGoal){
        try{
            HopeGoal hopeGoalEntity;
            if(!hopeGoal.getReferences_type().equals("")){
                hopeGoalEntity = HopeGoal.builder()
                        .title(hopeGoal.getTitle())
                        .goal_cost(hopeGoal.getGoal_cost())
                        .start_date(hopeGoal.getStart_date())
                        .end_date(hopeGoal.getEnd_date())
                        .username(hopeGoal.getUsername())
                        .references_type(hopeGoal.getReferences_type())
                        .build();
            }else{
                hopeGoalEntity = HopeGoal.builder()
                        .title(hopeGoal.getTitle())
                        .goal_cost(hopeGoal.getGoal_cost())
                        .start_date(hopeGoal.getStart_date())
                        .end_date(hopeGoal.getEnd_date())
                        .username(hopeGoal.getUsername())
                        .build();
            }
            hopeGoalRepository.save(hopeGoalEntity);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional
    public int update_hopeGoal(HopeGoal hopeGoal){
        try{
            HopeGoal hopeGoalEntity = HopeGoal.builder()
                    .hope_id(hopeGoal.getHope_id())
                    .title(hopeGoal.getTitle())
                    .goal_cost(hopeGoal.getGoal_cost())
                    .start_date(hopeGoal.getStart_date())
                    .end_date(hopeGoal.getEnd_date())
                    .username(hopeGoal.getUsername())
                    .references_type(hopeGoal.getReferences_type())
                    .build();
            hopeGoalRepository.save(hopeGoalEntity);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional
    public int delete_hopeGoal(long hope_id){
        try{
            hopeGoalRepository.deleteById(hope_id);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<HopeGoalModel> getHopeGoal(String username){
        return hopeGoalRepository.findByHopeGoal(username);
    }

    @Override
    public HopeGoalModel getHopeGoalDetail(long hope_id){
        return hopeGoalRepository.findByHopeGoalDetail(hope_id);
    }

    @Override
    public List<AccountBookOnlyTypeModel> getOnlyType(String username){
        return accountBookRepository.findByOnlyType(username);
    }

    @Override
    public Page<HopeGoalModel> test(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        System.out.println("pageable:"+pageable);
        String username = "Babo";
        return hopeGoalRepository.findAllByUsername(username, pageable);
    }
}

