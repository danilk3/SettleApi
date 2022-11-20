package com.example.settleapi.controllers;

import com.example.settleapi.domain.Apartment;
import com.example.settleapi.domain.Event;
import com.example.settleapi.domain.Filter;
import com.example.settleapi.parser.Parser;
import com.example.settleapi.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
        Event event = eventRepository.findById(eventId).get();
        Filter filter = Filter.newBuilder()
                .setCity(cityName)
                .setNumberOfRooms(numberOfRooms)
                .setFromPrice(fromPrice)
                .setToPrice(toPrice)
                .setSubwayStation(subway)
                .setApartmentType(apartmentType)
                .build();
        List<Apartment> apartments = new Parser(filter, event).getApartments();
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }
}
