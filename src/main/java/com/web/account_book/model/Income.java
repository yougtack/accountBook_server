package com.web.account_book.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long income_id;

    private String username;

    @CreationTimestamp
    private Timestamp income_date;
    private String income_where_to_get;
    private long income_cost;
    private String income_type;

    @Builder
    public Income(String username, String income_where_to_get, long income_cost, String income_type){
        this.username = username;
        this.income_where_to_get = income_where_to_get;
        this.income_cost = income_cost;
        this.income_type = income_type;
    }
}
