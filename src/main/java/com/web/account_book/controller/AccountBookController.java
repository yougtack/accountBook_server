package com.web.account_book.controller;

import com.web.account_book.model.*;
import com.web.account_book.model.entity.*;
import com.web.account_book.service.AccountBookService;
import com.web.account_book.util.LoginUtil;
import com.web.account_book.util.enums.HttpStatusEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.persistence.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
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
    public int insert_account_book(@RequestBody AccountBook accountBook, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(accountBook.getUser().getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.save_account_book(accountBook);
    }

    @PutMapping(value = "")
    public int update_account_book(@RequestBody AccountBook accountBook, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(accountBook.getUser().getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.update_account_book(accountBook);
    }

    @DeleteMapping(value = "/{ab_id}/{username}")
    public int delete_account_book(@PathVariable long ab_id, @PathVariable String username, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(username, session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.delete_account_book(ab_id);
    }

    //수익
    @GetMapping(value = "/income/{username}")
    public List<Income> incomeList(@PathVariable String username){
        return accountBookService.findByUsername(username);
    }

    @PostMapping(value = "/income")
    public int insert_income(@RequestBody Income income, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(income.getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.save_income(income);
    }

    @PutMapping(value = "/income")
    public int update_income(@RequestBody Income income, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(income.getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.update_income(income);
    }

    @DeleteMapping(value = "/income/{income_id}/{username}")
    public int delete_income(@PathVariable long income_id, @PathVariable String username, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(username, session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.delete_income(income_id);
    }

    //지출(사이드 바)
    @GetMapping(value = "/spending/{username}")
    public IncomeThisMonth total(@PathVariable String username){
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
    public int insert_budget(@RequestBody List<Budget> budgetList, HttpSession session, HttpServletResponse response){

        for(Budget budget: budgetList){
            if(!LoginUtil.login_check(budget.getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
                return 0;
            }
        }
        return accountBookService.save_budget(budgetList);
    }

    //예산 반성
    @GetMapping(value = "/budget/{username}/{start}/{end}")
    public List<BudgetLookBackModel> budget_look_back(@PathVariable String username, @PathVariable String start, @PathVariable String end){
        return accountBookService.budget_look_back(username, start, end);
    }

    @DeleteMapping(value = "/budget/{budget_id}/{username}")
    public int delete_budget(@PathVariable long budget_id, @PathVariable String username, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(username, session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
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

    //희망목표
    @PostMapping(value = "/hope_goal")
    public int insert_hopeGoal(@RequestBody HopeGoal hopeGoal, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(hopeGoal.getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.save_hopeGoal(hopeGoal);
    }

    @PutMapping(value = "/hope_goal")
    public int update_hopeGoal(@RequestBody HopeGoal hopeGoal, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(hopeGoal.getUsername(), session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.update_hopeGoal(hopeGoal);
    }

    @DeleteMapping(value = "/hope_goal/{hope_id}/{username}")
    public int delete_hopeGoal(@PathVariable long hope_id, @PathVariable String username, HttpSession session, HttpServletResponse response){
        if(!LoginUtil.login_check(username, session, response).equals(HttpStatusEnums.Success_200)) {
            return 0;
        }
        return accountBookService.delete_hopeGoal(hope_id);
    }

    @GetMapping(value = "/hope_goal/{username}")
    public List<HopeGoalModel> hopeGoal(@PathVariable String username){
        return accountBookService.getHopeGoal(username);
    }

    @GetMapping(value = "/hope_goal_detail/{hope_id}")
    public HopeGoalModel hopeGoalDetail(@PathVariable long hope_id){
        return accountBookService.getHopeGoalDetail(hope_id);
    }

    //타입만 보여주는 api
    @GetMapping(value = "/hope_goal_only_type/{username}")
    public List<AccountBookOnlyTypeModel> onlyType(@PathVariable String username){
        return accountBookService.getOnlyType(username);
    }

//    @GetMapping(value = "/test")
//    public Page<HopeGoalModel> test(@PageableDefault Pageable pageable){
//        return accountBookService.test(pageable);
//    }

    @PersistenceContext
    EntityManager em;

    @GetMapping(value = "/test")
    @Transactional
    public void test(){
        //스낵샷과 디비내용을 비교해서 기존과 값이 다르면 insert를 해주고 기본키가 같은 디비의 내용이 있을때는 update를 해준다.
//        User user1 = User.builder()
//                .username("asdasd")
//                .email("asd@asd.com")
//                .build();
//        em.persist(user1);
//        em.flush();
//        em.clear();

        List<User> users = em.createQuery("SELECT u FROM User AS u", User.class).getResultList();

        for(User user:users){
            System.out.println("username:"+user.getUsername());
            System.out.println("email:"+user.getEmail());

        }
    }
}


