package com.aikyamedge.employee1.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleTitle;
    @ManyToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

}
