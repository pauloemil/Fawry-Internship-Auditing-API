package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;



@Data
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Action> actions;

    private String user_name;
    private String user_image;
    private String user_title;


}