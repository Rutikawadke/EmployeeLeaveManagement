package com.aikyamedge.employee1.service;

import com.aikyamedge.employee1.constants.Constants;
import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.models.Leave;
import com.aikyamedge.employee1.repository.EmployeeRepository;
import com.aikyamedge.employee1.repository.LeaveRepository;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    @Autowired
    private  LeaveRepository leaveRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Optional<Leave> getLeaveById(Long id) {
        return leaveRepository.findById(id);
    }

    public Leave saveLeave(Leave leave,Long empId) {
        leave.setEmployee(employeeRepository.findById(empId).orElseThrow(()->new RuntimeException("Employee not found")));
        Integer leaveDays = (int) leave.getStartDate().until(leave.getEndDate().plusDays(1), ChronoUnit.DAYS);
        leave.setLeaveDays(leaveDays);
        return leaveRepository.save(leave);
    }

    public void deleteLeave(Long id) {
        Leave leave = getLeaveById(id).get();
        Clock cl = Clock.systemUTC();
        LocalDate lt = LocalDate.now(cl);
        if (leave.getStartDate().isAfter(lt)) {
            leaveRepository.deleteById(id);
        } else {
            throw new RuntimeException("Leave date is crossed");
        }
    }
    public Leave createLeaveByEmployeeId(Long empId, Leave leave) {
        Optional<Employee> employeeOptional = employeeRepository.findById(empId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            leave.setEmployee(employee);
            return leaveRepository.save(leave);
        } else {
            return null;
        }
    }



    public  Leave changeStatusOfLeave(Long leaveId, String status) {

        Optional<Leave> leaveOptional = leaveRepository.findById(leaveId);
        if(leaveOptional.isPresent()){
            Leave leave = leaveOptional.get();
            if ("APPROVED".equalsIgnoreCase(status)) {
                leave.setStatus(status);
                  Leave updatedLeave = leaveRepository.save(leave);
                Employee employee = updatedLeave.getEmployee();
                if (Constants.LeaveCount < employee.getLeaveCount()){
                    deductPaymentForEmployee(employee , updatedLeave.getLeaveDays());
                }
                employee.setLeaveCount(employee.getLeaveCount()+leave.getLeaveDays());
                employeeRepository.save(employee);
                return updatedLeave;
            } else {
                leave.setStatus(status);
               return leaveRepository.save(leave);
            }
        }
        throw new RuntimeException("Leave not found");
    }
    private int calculateExcessLeaves(Employee employee, int totalLeavesLimit) {
        return (employee.getLeaveCount()) - totalLeavesLimit;
        }
    private void deductPaymentForEmployee(Employee employee, int excessLeaves) {
        employee.setSalary(employee.getSalary()-(excessLeaves*(employee.getCurrentSalary()/22)));
        String message = "Payment deduction for " + excessLeaves + " excess leaves.>"+(employee.getCurrentSalary()-(employee.getSalary()));
        System.out.println(message);

    }
}
// if maxleave count < employee.getleavecount
// excessleaves = leave.getleaves