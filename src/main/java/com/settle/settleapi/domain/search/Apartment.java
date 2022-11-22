package com.settle.settleapi.domain.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Apartment {

    private String name;

    private String address;

    private String description;

    private String phoneOfOwner;

    private String coordinates;

    private String price;

    private String floor;

    private String numberOfBeds;

    private List<String> imagesUrl;
}
