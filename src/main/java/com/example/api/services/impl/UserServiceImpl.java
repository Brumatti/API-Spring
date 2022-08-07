package com.example.api.services.impl;

import com.example.api.domain.Users;
import com.example.api.repositories.UserRepository;
import com.example.api.services.UserService;
import com.example.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public Users findById(Integer id) {
        Optional<Users> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
    }
}