package com.mapzen.osrm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Route {
    JSONObject jsonObject;

    public Route(String jsonString) {
        jsonObject = new JSONObject(jsonString);
    }

    public int getTotalDistance() {
        return getSumary().getInt("total_distance");
    }

    public int getTotalTime() {
        return getSumary().getInt("total_time");
    }

    public Instruction getRouteInstruction() {
        return new Instruction(new JSONArray());
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
            double x = (double) lat / 1E5;
            double y = (double) lng / 1E5;
            double[] pair = {x, y};
            poly.add(pair);
        }
        return poly;
    }
}
