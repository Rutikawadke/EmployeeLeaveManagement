package com.aikyamedge.employee1.service;

import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.models.Leave;
import com.aikyamedge.employee1.repository.EmployeeRepository;
import com.aikyamedge.employee1.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    private  LeaveRepository leaveRepository;
    private EmployeeRepository employeeRepository;


    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Optional<Leave> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    public Leave saveLeave(Leave leave) {
        return leaveRepository.save(leave);
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }
    public Leave createLeaveByEmployeeId(Long empId, Leave leave) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            leave.setEmployee(employee);
            return leaveRepository.save(leave);
        } else {
            // Handle employee not found
            return null;
        }
    }

    public void changeStatusOfLeave(Long leaveId, String status) {
        Optional<Leave> leaveOptional = leaveRepository.findById(leaveId);
        leaveOptional.ifPresent(leave -> {
            leave.setStatus(status);
            leaveRepository.save(leave);
        });
    }
}