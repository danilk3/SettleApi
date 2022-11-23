package com.settle.settleapi.controllers;

import com.settle.settleapi.domain.Apartment;
import com.settle.settleapi.domain.Event;
import com.settle.settleapi.repos.ApartmentRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class ApartmentController {

    @Autowired
    ApartmentRepository apartmentRepository;

    @Operation(summary = "Пометить апартамент, как желаемый к просмотру, либо убрать его из просмотра.")
    @PutMapping("apartment/{apartment_id}/mark")
    @ResponseBody
    public ResponseEntity<Apartment> markOrUnmarkApartment(
            @PathVariable("apartment_id") Apartment apartment
    ) {
        log.info("Put method \"apartment/{apartment_id}/like\" called.");
        log.info(String.format("Liked apartment: %s", apartment));
        apartment.setMarkedForSelection(!apartment.getMarkedForSelection());
        return new ResponseEntity<>(apartmentRepository.save(apartment), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех подобранных апартаметов для мероприятия.")
    @GetMapping("apartment/{event_id}")
    @ResponseBody
    public ResponseEntity<Set<Apartment>> getAllApartments(
            @PathVariable("event_id") Event event
    ) {
        return new ResponseEntity<>(event.getApartments(), HttpStatus.OK);
    }

    @Operation(summary = "Получение всех апартаметов, которые были помечены как желаемые к просмотру.")
    @GetMapping("apartment/{event_id}/marked")
    @ResponseBody
    public ResponseEntity<Set<Apartment>> getAllMarkedApartments(
            @PathVariable("event_id") Event event
    ) {
        Set<Apartment> apartments = event.getApartments().stream().filter(Apartment::getMarkedForSelection).collect(Collectors.toSet());
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }
}
