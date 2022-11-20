package com.example.settleapi.controllers;

import com.example.settleapi.domain.Event;
import com.example.settleapi.domain.User;
import com.example.settleapi.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        System.out.println("Creating new event: " + event);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.CREATED);
    }

//    @GetMapping("user/{user_id}/events")
//    @ResponseBody
//    public ResponseEntity<Set<Event>> getEventsOfUser(@PathVariable("user_id") User user) {
//        return new ResponseEntity<>(user.getActiveEvents(), HttpStatus.OK);
//    }
}
