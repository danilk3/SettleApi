package com.settle.settleapi.controllers;

import com.settle.settleapi.domain.Apartment;
import com.settle.settleapi.domain.Event;
import com.settle.settleapi.domain.search.Filter;
import com.settle.settleapi.parser.Parser;
import com.settle.settleapi.repos.ApartmentRepository;
import com.settle.settleapi.repos.EventRepository;
import io.swagger.v3.oas.annotations.Operation;
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

    @Autowired
    ApartmentRepository apartmentRepository;

    @Operation(summary = "Подбор апартаментов.")
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
        log.info("Get method \"search/\" called.");
        log.info(String.format("Scrapping cian.ru with parameters: city_name=%s; number_of_rooms=%d; floor_number=%d; from_price=%d; to_price=%d; apart_type=%s; subway=%s; event_id=%d", cityName, numberOfRooms, floorNumber, fromPrice, toPrice, apartmentType, subway, eventId));
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
        apartmentRepository.saveAll(apartments);
        return new ResponseEntity<>(apartments, HttpStatus.OK);
    }
}
