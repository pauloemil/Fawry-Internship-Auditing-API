package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@Entity
@Table(name = "action")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long action_id;
    @ManyToOne
    @JoinColumn(name="action_type_id", nullable=false)
    private ActionType action_type;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="application_id", nullable=false)
    private Application application;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="business_entity_id", nullable=false)
    private BusinessEntity business_entity;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy="action")
    private List<Parameter> parameters;

    private String description_ar;
    private String description_en;
    private Timestamp date;
    @JsonIgnore
    private String trace_id;

    @Override
    public String toString() {
        return "Action{" +
                "action_id=" + action_id +
                '}';
    }

}
