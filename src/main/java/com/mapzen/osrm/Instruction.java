package com.mapzen.osrm;

import org.json.JSONArray;

public class Instruction {
    JSONArray json;

    public Instruction(JSONArray json) {
        this.json = json;
    }

    public String getTurnInstruction() {
        return json.getString(0);
    }

    public String getName() {
        return json.getString(1);
    }

    public int getDistance() {
        return json.getInt(2);
    }

    public String getDirection() {
        return json.getString(6);
    }
}
