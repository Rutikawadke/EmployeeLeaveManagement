package com.aikyamedge.employee1.dto;

import com.aikyamedge.employee1.models.Employee;
import com.aikyamedge.employee1.models.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetail implements UserDetails {


    private String email;
    private List<GrantedAuthority> grantedAuthorities;
    private String password;
    public UserDetail(Employee user) {
        List<Role> roles = user.getRoles();
        String[] arrayOfRoles = new String[roles.size()];

        for(int i = 0; i < roles.size(); i++){
            arrayOfRoles[i] = roles.get(i).getRoleTitle();
        }
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.grantedAuthorities = Arrays.stream(arrayOfRoles)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}