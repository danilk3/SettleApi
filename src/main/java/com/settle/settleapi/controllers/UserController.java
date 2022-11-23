package com.settle.settleapi.controllers;

import com.settle.settleapi.domain.Event;
import com.settle.settleapi.domain.User;
import com.settle.settleapi.repos.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Создание пользователя.")
    @PostMapping("user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Post method \"user\" called.");
        log.info("Creating new user: " + user);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Получение пользователя по user_id.")
    @GetMapping("user/")
    @ResponseBody
    public ResponseEntity<User> getUser(
            @RequestParam("user_id") User user
    ) {
        log.info("Get method \"user\" called with param user_id = " + user.getUserId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Получение активных мероприятий пользователя.")
    @GetMapping("user/{user_id}/events")
    @ResponseBody
    public ResponseEntity<Set<Event>> getUsersOfEvents(
            @PathVariable("user_id") User user
    ) {
        log.info("Get method \"user/{user_id}/events\" called.");
        return new ResponseEntity<>(user.getActiveEvents(), HttpStatus.OK);
    }
}
