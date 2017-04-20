package com.example.rajashrk.weatherapp.model;

import java.util.List;

public class SearchResult {
    private String message;
    private String cod;
    private Integer count;
    private List<SearchCityDetails> list;

    public List<SearchCityDetails> getList() {
        return list;
    }

    public class SearchCityDetails {
        private Integer id;
        private String name;
        private Integer dt;
        private Sys sys;
        private LatLng coord;

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCountryName() {
            return sys.getCountry();
        }

        public LatLng getCoord() {
            return coord;
        }
    }

    private class Sys {
        private String country;

        String getCountry() {
            return country;
        }
    }

    public class LatLng {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }
    }
}