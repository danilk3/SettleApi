package com.example.settleapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
//@Entity
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id")
//    private Event event;

    private String name;

    private String address;

    private String description;

    private String contacts;

    private String coordinates;

    @Column(name = "price_per_night")
    private String pricePerNight;

    private String floor;

    @Column(name = "number_of_beds")
    private String numberOfBeds;

    @Column(name = "image_urls")
    private List<String> imagesUrl;
}
