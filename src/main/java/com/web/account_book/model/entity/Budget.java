package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    private Timestamp insertDate;

    private String username;
    private long budget;
    private String budget_type;

    @Builder
    public Budget(String username, long budget, String budget_type){
        this.username = username;
        this.budget = budget;
        this.budget_type = budget_type;
    }
}
