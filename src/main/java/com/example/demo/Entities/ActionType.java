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
@Table(name = "action_type")
public class ActionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long action_type_id;

    @JsonIgnore
    @OneToMany(mappedBy="action_type")
    private List<Action> actions;

    @JsonIgnore
    @Column(unique = true)
    private String code;
    @Column(unique = true)
    private String name_ar;
    @Column(name = "name_en", unique = true)
    private String name;
    @JsonIgnore
    private String message_template_ar;
    @JsonIgnore
    private String message_template_en;

}
