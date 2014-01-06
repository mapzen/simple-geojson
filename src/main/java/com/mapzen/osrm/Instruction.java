package com.mapzen.osrm;

import org.json.JSONArray;
import org.json.JSONException;

public class Instruction {
    public static String NoTurn = "No Turn"; // 0;          //Give no instruction at all
    public static String GoStraight = "Go Straight"; //1;      //Tell user to go straight!
    public static String TurnSlightRight = "Turn Slight Right"; //2;
    public static String TurnRight = "Turn Right"; // 3;
    public static String TurnSharpRight = "Turn Sharp Right"; // 4;
    public static String UTurn = "U Turn"; // 5;
    public static String TurnSharpLeft = "Turn Sharp Left"; // 6;
    public static String TurnLeft = "Turn Left"; // 7;
    public static String TurnSlightLeft = "Turn Slight Left"; // 8;
    public static String ReachViaPoint = "Reach Via Point"; // 9;
    public static String HeadOn = "Head On"; // 10;
    public static String EnterRoundAbout = "Enter Round About"; // 11;
    public static String LeaveRoundAbout = "LEave Round About"; // 12;
    public static String StayOnRoundAbout = "Stay On Round About"; // 13;
    public static String StartAtEndOfStreet = "Start At End Of Street"; // 14;
    public static String ReachedYourDestination = "Reached Your Destination"; // 15;
    public static String EnterAgainstAllowedDirection = "Enter Against Allowed Direction"; // 16;
    public static String LeaveAgainstAllowedDirection = "Leave Against Allowed Direction"; // 17;

    public static int METERS_IN_MILE = 1609;

    private JSONArray json;
    private int turn, distance;
    private double[] point = {};

    public Instruction(JSONArray json) {
        if (json.length() < 8) {
            throw new JSONException("too few arguments");
        }
        this.json = json;
        setTurnInstruction(json.getInt(0));
        setDistance(json.getInt(2));
    }

    public void setTurnInstruction(int turn) {
        this.turn = turn;
    }

    public int getTurnInstruction() {
        return turn;
    }

    public String getHumanTurnInstruction() {
        String[] decodedInstructions = { NoTurn, GoStraight, TurnSlightRight, TurnRight, TurnSharpRight, UTurn,
            TurnSharpLeft, TurnLeft, TurnSlightLeft, ReachViaPoint, HeadOn, EnterRoundAbout, LeaveRoundAbout,
            StayOnRoundAbout, StartAtEndOfStreet, ReachedYourDestination, EnterAgainstAllowedDirection,
            LeaveAgainstAllowedDirection
        };
        return decodedInstructions[turn];
    }

    public String getName() {
        return json.getString(1);
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public int getDistanceInMiles() {
        return distance / METERS_IN_MILE;
    }

    public String getDirection() {
        return json.getString(6);
    }

    public float getDirectionAngle() {
        String direction = getDirection();
        float angle = 0;
        if (direction.equals("NE")) {
            angle = 315.0f;
        } else if (direction.equals("E")) {
            angle = 270.0f;
        } else if (direction.equals("SE")) {
            angle = 225.0f;
        } else if (direction.equals("S")) {
            angle = 180.0f;
        } else if (direction.equals("SW")) {
            angle = 135.0f;
        } else if (direction.equals("W")) {
            angle = 90.0f;
        } else if (direction.equals("NW")) {
            angle = 45.0f;
        }
        return angle;
    }

    public int getBearing() {
        return 360 - json.getInt(7);
    }

    public double[] getPoint() {
        return point;
    }

    public void setPoint(double[] point) {
        this.point = point;
    }
}
