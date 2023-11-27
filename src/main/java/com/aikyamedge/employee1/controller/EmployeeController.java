package com.aikyamedge.employee1.controller;

import com.aikyamedge.employee1.dto.AuthRequest;
import com.aikyamedge.employee1.dto.EmployeeDto;
import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.service.EmployeeService;
import com.aikyamedge.employee1.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    @GetMapping("/admin/employees")
    public List<Employee> getAllEmployees() {
        List<Employee> list = employeeService.getAllEmployees();
        for (Employee employee : list){
            String email = employee.getEmail();
            System.out.println(email);
        }
        return list;
    }

    @GetMapping("/any/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/any/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);

//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee/employees/{empId}/leaveCount")
    public ResponseEntity<Integer> getLeaveCount(@PathVariable Long empId) {
        int leaveCount = employeeService.getLeaveCount(empId);
        return new ResponseEntity<>(leaveCount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) throws ExpiredJwtException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {

            return jwtService.generateToken(authRequest.getEmail());
        }

        return "Invalid credentials.";
    }
    @PatchMapping("/admin/{Id}/updateSalary")
    public ResponseEntity<Employee> updateEmployeeSalary(
            @PathVariable("Id") Long employeeId,
            @RequestParam("salary") int newSalary) {

        Employee updatedEmployee = employeeService.setEmployeeCurrentSalary(employeeId, newSalary);

        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}