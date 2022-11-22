package com.example.settleapi.domain.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Filter {

    private String city;

    private String apartmentType;

    private Integer numberOfRooms;

    private Integer minFloor;

    private Integer fromPrice;

    private Integer toPrice;

    private String subwayStation;
}
