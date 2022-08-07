package com.example.api.resources;


import com.example.api.domain.Users;
import com.example.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Users> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findById(id));


        //Users obj = this.service.findById(id);
        //return ResponseEntity.ok().body(obj);
    }
}