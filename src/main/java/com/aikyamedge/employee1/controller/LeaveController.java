package com.aikyamedge.employee1.controller;

import com.aikyamedge.employee1.models.Leave;
import com.aikyamedge.employee1.repository.LeaveRepository;
import com.aikyamedge.employee1.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public List<Leave> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/leaves/{id}")
    public ResponseEntity<Leave> getLeaveById(@PathVariable Long id) {
        Optional<Leave> leave = leaveService.getLeaveById(id);
        return leave.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/employee/leaves/{empId}/createLeave")
    public ResponseEntity<Leave> createLeaveByEmployeeId(@PathVariable("empId") Long empId, @RequestBody Leave leave) {
        Leave savedLeave = leaveService.saveLeave(leave,empId);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @PutMapping("/admin/leaves/{leaveId}/changeStatus")
    public ResponseEntity<Leave> changeStatusOfLeave(@PathVariable("leaveId") Long leaveId, @RequestParam("status") String status) {
         Leave updatedLeave = leaveService.changeStatusOfLeave(leaveId, status);
        return new ResponseEntity<>(updatedLeave, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
