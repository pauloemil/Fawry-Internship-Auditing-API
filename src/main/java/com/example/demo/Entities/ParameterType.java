package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parameter_type")
public class ParameterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parameter_type_id;
    @JsonIgnore
    @OneToMany(mappedBy="parameter_type")
    private List<Parameter> parameters;
    @JsonIgnore
    @Column(unique = true)
    private String code;
    @Column(unique = true)
    private String name_ar;
    @Column(name = "name_en", unique = true)
    private String name;

}
