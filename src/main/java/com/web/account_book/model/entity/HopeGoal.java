package com.web.account_book.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class HopeGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long hope_id;

    String title;
    int goal_cost;
    String start_date;
    String end_date;
    String username;
    String references_type;

    @UpdateTimestamp
    private Timestamp createDate;

    @Builder
    public HopeGoal(long hope_id, String title, int goal_cost, String start_date, String end_date,
                    String username, String references_type){
        this.hope_id = hope_id;
        this.title = title;
        this.goal_cost = goal_cost;
        this.start_date = start_date;
        this.end_date = end_date;
        this.username = username;
        this.references_type = references_type;
    }
}
