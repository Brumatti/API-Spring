package com.example.api.services;

import com.example.api.domain.Users;

public interface UserService {
    Users findById(Integer id);
}
