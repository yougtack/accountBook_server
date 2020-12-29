package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long budget_id;

    private String insert_date;
    private String username;
    private long budget;
    private String budget_type;

    @Builder
    public Budget(long budget_id, String insert_date, String username, long budget, String budget_type){
        this.budget_id = budget_id;
        this.insert_date = insert_date;
        this.username = username;
        this.budget = budget;
        this.budget_type = budget_type;
    }
}
