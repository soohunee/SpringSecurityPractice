package com.security.practice.demo.service;

import com.security.practice.demo.domain.Role;
import com.security.practice.demo.domain.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    List<User> getUsers();
}
