package com.mapzen.osrm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Route {
    JSONObject jsonObject;

    public Route() {
    }

    public Route(String jsonString) {
        setJsonObject(new JSONObject(jsonString));
    }

    public Route(JSONObject jsonObject) {
        setJsonObject(jsonObject);
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public int getTotalDistance() {
        return getSumary().getInt("total_distance");
    }

    public int getTotalTime() {
        return getSumary().getInt("total_time");
    }

    public Instruction getRouteInstruction() {
        JSONArray jsonArray = new JSONArray("[1,2,3,4,5,6,7,8]");
        return new Instruction(jsonArray);
    }

    public JSONArray getRouteInstructions() {
        return jsonObject.getJSONArray("route_instructions");
    }

    public ArrayList<double[]> getGeometry() {
        return decodePolyline(jsonObject.getString("route_geometry"));
    }

    private JSONObject getSumary() throws JSONException {
       return jsonObject.getJSONObject("route_summary");
    }

    private ArrayList<double[]> decodePolyline(String encoded) {
        ArrayList<double[]> poly = new ArrayList<double[]>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            double x = (double) lat / 1E6;
            double y = (double) lng / 1E6;
            double[] pair = {x, y, 0};
            if (!poly.isEmpty()) {
                double[] lastElement = poly.get(poly.size()-1);
                double distance = distanceBetweenPoints(pair, lastElement);
                double totalDistance = distance + lastElement[2];
                pair[2] = totalDistance;
            }
            poly.add(pair);
        }
        return poly;
    }

    private double distanceBetweenPoints(double[] pointA, double[] pointB) {
        double R = 6371;
        double lat = toRadian(pointB[0] - pointA[0]);
        double lon = toRadian(pointB[1] - pointA[1]);
        double a = Math.sin(lat / 2) * Math.sin(lat / 2) +
                Math.cos(toRadian(pointA[0])) * Math.cos(toRadian(pointB[0])) *
                        Math.sin(lon / 2) * Math.sin(lon / 2);
        double c = 2 * Math.asin(Math.min(1, Math.sqrt(a)));
        double d = R * c;
        return d * 1000;
    }

    private double toRadian(double val) {
        return (Math.PI / 180) * val;
    }

}
