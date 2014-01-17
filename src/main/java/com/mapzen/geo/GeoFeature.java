package com.mapzen.geo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class GeoFeature {
    private HashMap<String, String> properties = new HashMap<String, String>();
    private Geometry geometry = new Geometry();

    public GeoFeature() {
    }

    public void buildFromJSON(JSONObject json) throws JSONException {
        JSONObject properties = json.getJSONObject("properties");
        Iterator<String> iterator = properties.keys();
        while(iterator.hasNext()) {
            String key = iterator.next();
            setProperty(key, properties.getString(key));
        }
        JSONObject geometry = json.getJSONObject("geometry");
        JSONArray coordinates = geometry.getJSONArray("coordinates");
        setLat(coordinates.getDouble(1));
        setLon(coordinates.getDouble(0));
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