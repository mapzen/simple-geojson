package com.mapzen.osrm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Route {
    private ArrayList<double[]> poly = null;
    private ArrayList<Instruction> turnByTurn = null;
    private JSONArray instructions;
    private JSONObject jsonObject;

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
        if (foundRoute()) {
            this.instructions = this.jsonObject.getJSONArray("route_instructions");
            initializeTurnByTurn();
        }
    }

    public int getTotalDistance() {
        return getSumary().getInt("total_distance");
    }

    public int getStatus() {
        return jsonObject.getInt("status");
    }

    public boolean foundRoute() {
        return getStatus() == 0;
    }

    public int getTotalTime() {
        return getSumary().getInt("total_time");
    }

    private void initializeTurnByTurn() {
        turnByTurn = new ArrayList<Instruction>();
        for(int i = 0; i < instructions.length(); i++) {
            Instruction instruction = new Instruction(instructions.getJSONArray(i));
            turnByTurn.add(instruction);
        }
    }

    public ArrayList<Instruction> getRouteInstructions() {
        double[] pre = null;
        double distance = 0;
        double totalDistance = 0;
        double[] markerPoint = {0, 0};

        int marker = 1;
        ArrayList<double[]> geometry = getGeometry();
        // set initial point to first instruction
        turnByTurn.get(0).setPoint(geometry.get(0));
        for(int i = 0; i < geometry.size(); i++) {
            double[] f = geometry.get(i);
            if(marker == turnByTurn.size()) {
                continue;
            }
            Instruction instruction = turnByTurn.get(marker);
            if(pre != null) {
                distance = f[2] - pre[2];
                totalDistance += distance;
            }
            // this needs the previous distance marker hence minus one
            if(Math.floor(totalDistance) > turnByTurn.get(marker-1).getDistance()) {
                instruction.setPoint(markerPoint);
                marker++;
                totalDistance = distance;
            }
            markerPoint = new double[]{f[0], f[1]};
            pre = f;

            // setting the last one to the destination
            if(geometry.size() - 1 == i) {
                turnByTurn.get(marker).setPoint(markerPoint);
            }
        }
        return turnByTurn;
    }

    public ArrayList<double[]> getGeometry() {
        return decodePolyline(jsonObject.getString("route_geometry"));
    }

    public double[] getStartCoordinates() {
        JSONArray points = getViaPoints().getJSONArray(0);
        double[] coordinates = {
            points.getDouble(0),
            points.getDouble(1)
        };
        return coordinates;
    }

    private JSONArray getViaPoints() {
        return jsonObject.getJSONArray("via_points");
    }

    private JSONObject getSumary() throws JSONException {
       return jsonObject.getJSONObject("route_summary");
    }

    private ArrayList<double[]> decodePolyline(String encoded) {
        if (poly == null) {
            poly = new ArrayList<double[]>();
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
