package com.example.settleapi.domain;

import lombok.Data;

@Data
public class Filter {

    private String city;

    private String apartmentType;

    private Integer numberOfRooms;

    private Integer floor;

    private Integer fromPrice;

    private Integer toPrice;

    private String subwayStation;

    public static Builder newBuilder() {
        return new Filter().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setCity(String city) {
            Filter.this.city = city;
            return this;
        }

        public Builder setApartmentType(String apartmentType) {
            Filter.this.apartmentType = apartmentType;
            return this;
        }

        public Builder setNumberOfRooms(Integer numberOfRooms) {
            Filter.this.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder setFloor(Integer floor) {
            Filter.this.floor = floor;
            return this;
        }

        public Builder setFromPrice(Integer fromPrice) {
            Filter.this.fromPrice = fromPrice;
            return this;
        }

        public Builder setToPrice(Integer toPrice) {
            Filter.this.toPrice = toPrice;
            return this;
        }

        public Builder setSubwayStation(String subwayStation) {
            Filter.this.subwayStation = subwayStation;
            return this;
        }

        public Filter build() {
            return Filter.this;
        }
    }
}
