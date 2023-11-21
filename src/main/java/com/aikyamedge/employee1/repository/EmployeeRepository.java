package com.aikyamedge.employee1.repository;

import com.aikyamedge.employee1.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    // Additional query methods if needed
}