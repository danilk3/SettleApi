package com.example.settleapi.controllers;

import com.example.settleapi.domain.search.Apartment;
import com.example.settleapi.domain.Event;
import com.example.settleapi.domain.search.Filter;
import com.example.settleapi.parser.Parser;
import com.example.settleapi.repos.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class SearchController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("search/")
    public ResponseEntity<List<Apartment>> searchApartments(
            @RequestParam(name = "city_name") String cityName,
            @RequestParam(name = "number_of_rooms") Integer numberOfRooms,
            @RequestParam(name = "floor_number") Integer floorNumber,
            @RequestParam(name = "from_price") Integer fromPrice,
            @RequestParam(name = "to_price") Integer toPrice,
            @RequestParam(name = "apart_type") String apartmentType,
            @RequestParam(name = "subway") String subway,
            @RequestParam(name = "event_id") Long eventId
    ) throws IOException {
        log.debug("Get method \"search/\" called.");
        log.debug("Scrapping cian.ru with parameters: city_name=%s; number_of_rooms=%d; floor_number=%d; from_price=%d; to_price=%d; apart_type=%s; subway=%s; event_id=%d", cityName, numberOfRooms, floorNumber, fromPrice, toPrice, apartmentType, subway, eventId);
        Event event = eventRepository.findById(eventId).get();
        Filter filter = Filter.builder()
                .city(cityName)
                .numberOfRooms(numberOfRooms)
                .fromPrice(fromPrice)
                .toPrice(toPrice)
                .subwayStation(subway)
                .apartmentType(apartmentType)
                .minFloor(floorNumber)
                .build();
        List<Apartment> apartments = new Parser(filter, event).getApartments();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }
}
