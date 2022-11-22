package com.settle.settleapi.controllers;

import com.settle.settleapi.domain.Event;
import com.settle.settleapi.domain.User;
import com.settle.settleapi.repos.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // Create new event
    @PostMapping("event")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        log.debug("Post method \"event\" called.");
        log.debug("Creating new event: " + event);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.CREATED);
    }

    // Get all users of event.
    @GetMapping("event/{event_id}/users")
    @ResponseBody
    public ResponseEntity<Set<User>> getUsersOfEvents(@PathVariable("event_id") Event event) {
        log.debug("Get method \"event/{event_id}/users\" called.");
        return new ResponseEntity<>(event.getParticipants(), HttpStatus.OK);
    }

    // Добавление пользователя в мероприятие
    @PutMapping("event/{event_id}/user/{user_id}")
    @ResponseBody
    public ResponseEntity<Event> addUserToEvent(
            @PathVariable("event_id") Event event,
            @PathVariable("user_id") User user
    ) {
        log.debug("Put method \"event/{event_id}/user/{user_id}\" called.");
        log.debug(String.format("Added new user %s to event %s", user, event));
        event.addUser(user);
        return new ResponseEntity<>(eventRepository.save(event), HttpStatus.OK);
    }
}
