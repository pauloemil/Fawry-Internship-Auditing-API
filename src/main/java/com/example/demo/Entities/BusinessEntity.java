package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "business_entity")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long business_entity_id;


    @JsonIgnore
    @OneToMany(mappedBy="business_entity")
    private List<Action> actions;

    private String business_entity_name;


}