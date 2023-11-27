package com.aikyamedge.employee1.service;

import com.aikyamedge.employee1.constants.Constants;
import com.aikyamedge.employee1.dto.EmployeeDto;
import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.models.Role;
import com.aikyamedge.employee1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee emp = new Employee();
        emp.setFirstName(employeeDto.getFirstName());
        emp.setLastName(employeeDto.getLastName());
        emp.setAddress(employeeDto.getAddress());
        emp.setEmail(employeeDto.getEmail());
        emp.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        Role defaultRole = new Role();
        defaultRole.setRoleTitle(Constants.EMPLOYEE);
        defaultRole.setEmployee(emp);
        emp.setRoles(Collections.singletonList(defaultRole));

        emp.setLeaveCount(Constants.LeaveCount);
        return employeeRepository.save(emp);
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
    public Employee setEmployeeCurrentSalary(Long employeeId, int newSalary) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setCurrentSalary(newSalary);
            employee.setSalary(newSalary);
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }

}

