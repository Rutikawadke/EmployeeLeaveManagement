package com.aikyamedge.employee1.service.Impl;

import com.aikyamedge.employee1.dto.UserDetail;
import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private EmployeeService userServiceDetails;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> userOptional = userServiceDetails.getByEmail(email);
        return userOptional.map(UserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));
    }
}
