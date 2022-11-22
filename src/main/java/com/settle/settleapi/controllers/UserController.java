package com.settle.settleapi.controllers;

import com.settle.settleapi.domain.Event;
import com.settle.settleapi.domain.User;
import com.settle.settleapi.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Create new user.
    @PostMapping("user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.debug("Post method \"user\" called.");
        log.debug("Creating new user: " + user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    // Get user by id.
    @GetMapping("user/")
    @ResponseBody
    public ResponseEntity<User> getUser(@RequestParam("user_id") User user) {
        log.debug("Post Get \"user\" called.");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Get active events of the user.
    @GetMapping("user/{user_id}/events")
    @ResponseBody
    public ResponseEntity<Set<Event>> getUsersOfEvents(@PathVariable("user_id") User user) {
        log.debug("Get method \"user/{user_id}/events\" called.");
        return new ResponseEntity<>(user.getActiveEvents(), HttpStatus.OK);
    }
}
