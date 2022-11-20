package com.example.settleapi.controllers;

import com.example.settleapi.domain.Event;
import com.example.settleapi.domain.User;
import com.example.settleapi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Creating new user: " + user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("user_id") User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @GetMapping("event/{user_id}/users")
//    @ResponseBody
//    public ResponseEntity<Set<User>> getUsersOfEvents(@PathVariable("event_id") Event event) {
//        return new ResponseEntity<>(event.getParticipants(), HttpStatus.OK);
//    }
}
