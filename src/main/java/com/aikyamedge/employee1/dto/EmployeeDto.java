package com.aikyamedge.employee1.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private String FirstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Long currentSalary;
}
