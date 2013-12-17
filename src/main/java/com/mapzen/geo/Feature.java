package com.mapzen.geo;

import java.util.HashMap;

public class Feature {
    private HashMap<String, String> properties = new HashMap<String, String>();
    private Geometry geometry = new Geometry();

    public Feature() {
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public double getLon() {
        return geometry.getLon();
    }

    public double getLat() {
        return geometry.getLat();
    }

    public void setLon(double lon) {
        geometry.setLon(lon);
    }

    public void setLat(double lat) {
        geometry.setLat(lat);
    }
}