package com.aikyamedge.employee1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String password;
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "leave_count")
    private Integer leaveCount;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
    private List<Leave> leaves;

    @Column(name = "current_salary")
    private Integer currentSalary;

    @Column(name = "salary_to_pay")
    private Integer salary;

    // Getters and setters
}




    // Constructors
