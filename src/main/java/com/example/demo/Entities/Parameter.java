package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parameter")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parameter_id;
    @ManyToOne
    @JoinColumn(name="parameter_type_id", nullable=false)
    private ParameterType parameter_type;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="action_id", nullable=false)
    private Action action;
    private String parameter_value;
}
