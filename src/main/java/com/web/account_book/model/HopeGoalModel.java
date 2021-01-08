package com.web.account_book.model;

import java.util.Date;

public interface HopeGoalModel {
    long getHope_id();
    String getTitle();
    int getGoal_cost();
    String getUsername();
    String getStart_date();
    String getEnd_date();
    Date getCreate_date();
    String getReferences_type();
    int getSum_cost();
}