package com.aikyamedge.employee1.service;

import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private  EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public String deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            return "Employee with ID " + id + " deleted successfully.";
        } else {
            return "No data available for Employee with ID " + id + ". No deletion performed.";
        }
    }
    public int getLeaveCount(Long empId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        return employeeOptional.map(employee -> employee.getLeaves().size()).orElse(0);
    }
    public Optional<Employee> getByEmail(String email){
        return employeeRepository.findByEmail(email);

    }
}

