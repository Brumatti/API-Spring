package com.example.api.services;

import com.example.api.domain.Users;
import com.example.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {
    Users findById(Integer id);
    List<Users> findAll();
    Users create(UserDTO OBJ);
}
