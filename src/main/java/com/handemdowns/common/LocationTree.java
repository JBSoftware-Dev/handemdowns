package com.handemdowns.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocationTree {
    private String country;
    private List<ProvState> provStates = new ArrayList<>();

    public LocationTree(String country) {
        this.country = country;
    }

    @Data
    @AllArgsConstructor
    public static class ProvState {
        private String name;
        private List<City> cities = new ArrayList<>();
        public ProvState(String name) {
            this.name = name;
        }
    }

    @Data
    @AllArgsConstructor
    public static class City {
        private String name;
        private List<Area> areas = new ArrayList<>();
        public City(String name) {
            this.name = name;
        }
    }

    @Data
    public static class Area {
        private String name;
        public Area(String name) {
            this.name = name;
        }
    }
}