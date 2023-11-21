package com.aikyamedge.employee1.repository;

import com.aikyamedge.employee1.models.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // You can add custom query methods if needed
}