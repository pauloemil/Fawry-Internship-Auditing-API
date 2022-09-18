package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application")
public class Application {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long application_id;

    @JsonIgnore
    @OneToMany(mappedBy="application")
    private List<Action> actions;

    private String application_name;


}
