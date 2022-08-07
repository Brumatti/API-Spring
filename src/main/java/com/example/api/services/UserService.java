package com.example.api.services;

import com.example.api.domain.Users;

import java.util.List;

public interface UserService {
    Users findById(Integer id);
    List<Users> findAll();
}
