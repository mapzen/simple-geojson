package com.mapzen.osrm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private JSONObject getSumary() throws JSONException {
       return jsonObject.getJSONObject("route_summary");
    }

}
