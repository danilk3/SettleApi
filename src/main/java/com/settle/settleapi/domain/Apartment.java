package com.settle.settleapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_id")
    private Long id;

    private String name;

    private String address;

    @Column(length = 2048)
    private String description;

    @Column(name = "phone_of_owner")
    private String phoneOfOwner;

    private String price;

    private String floor;

    @Column(name = "number_of_beds")
    private String numberOfBeds;

    @Column(name = "marked_for_selection")
    private Boolean markedForSelection = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @ElementCollection
    @CollectionTable(name="image_urls", joinColumns=@JoinColumn(name="image_id"))
    @Column(name="image_url")
    private List<String> imageUrls;
}
