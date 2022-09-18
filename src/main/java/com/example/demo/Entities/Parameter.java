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


    public long getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(long parameter_id) {
        this.parameter_id = parameter_id;
    }

    public ParameterType getParameter_type() {
        return parameter_type;
    }

    public void setParameter_type(ParameterType parameter_type) {
        this.parameter_type = parameter_type;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }
}
