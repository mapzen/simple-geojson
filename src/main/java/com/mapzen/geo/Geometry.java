package com.mapzen.geo;

public class Geometry {
    private double lat, lon;

    public String getType() {
        return "Point";
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
