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

    private JSONArray json;
    private int turn;

    public Instruction(JSONArray json) {
        if (json.length() < 8) {
            throw new JSONException("too few arguments");
        }
        this.json = json;
        setTurnInstruction(json.getInt(0));
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

    public int getDistance() {
        return json.getInt(2);
    }

    public String getDirection() {
        return json.getString(6);
    }
}
