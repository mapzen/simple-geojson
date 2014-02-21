package com.mapzen.osrm;

import com.mapzen.geo.DistanceFormatter;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

public class Instruction {
    public static final String NO_TURN = "No turn"; // 0; (Give no instruction at all)
    public static final String GO_STRAIGHT = "Continue on"; //1; (Tell user to go straight!)
    public static final String TURN_SLIGHT_RIGHT = "Make a slight right on to"; //2;
    public static final String TURN_RIGHT = "Make a right on to"; // 3;
    public static final String TURN_SHARP_RIGHT = "Make a sharp right on to"; // 4;
    public static final String U_TURN = "U Turn"; // 5;
    public static final String TURN_SHARP_LEFT = "Make a sharp left on to"; // 6;
    public static final String TURN_LEFT = "Make a left on to"; // 7;
    public static final String TURN_SLIGHT_LEFT = "Make a slight left on to"; // 8;
    public static final String REACH_VIA_POINT = "Reach via point"; // 9;
    public static final String HEAD_ON = "Head on"; // 10;
    public static final String ENTER_ROUND_ABOUT = "Enter round about"; // 11;
    public static final String LEAVE_ROUND_ABOUT = "Leave round about"; // 12;
    public static final String STAY_ON_ROUND_ABOUT = "Stay on round about"; // 13;
    public static final String START_AT_END_OF_STREET = "Start at end of street"; // 14;
    public static final String REACHED_YOUR_DESTINATION = "You have reached your destination"; // 15;
    public static final String ENTER_AGAINST_ALLOWED_DIRECTION = "Enter against allowed direction"; // 16;
    public static final String LEAVE_AGAINST_ALLOWED_DIRECTION = "Leave against allowed direction"; // 17;

    public static String[] decodedInstructions = {NO_TURN, GO_STRAIGHT, TURN_SLIGHT_RIGHT,
            TURN_RIGHT, TURN_SHARP_RIGHT, U_TURN, TURN_SHARP_LEFT, TURN_LEFT, TURN_SLIGHT_LEFT,
            REACH_VIA_POINT, HEAD_ON, ENTER_ROUND_ABOUT, LEAVE_ROUND_ABOUT, STAY_ON_ROUND_ABOUT,
            START_AT_END_OF_STREET, REACHED_YOUR_DESTINATION, ENTER_AGAINST_ALLOWED_DIRECTION,
            LEAVE_AGAINST_ALLOWED_DIRECTION
    };

    private JSONArray json;
    private int turn, distanceInMeters;
    private double[] point = {};

    public Instruction(JSONArray json) {
        if (json.length() < 8) {
            throw new JSONException("too few arguments");
        }
        this.json = json;
        setTurnInstruction(parseTurnInstruction(json));
        setDistance(json.getInt(2));
    }

    /**
     * Used for testing. Do not remove.
     */
    protected Instruction() {
    }

    public void setTurnInstruction(int turn) {
        this.turn = turn;
    }

    public int getTurnInstruction() {
        return turn;
    }

    public String getHumanTurnInstruction() {
        return decodedInstructions[turn];
    }

    public String getName() {
        return json.getString(1);
    }

    public void setDistance(int distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public int getDistance() {
        return distanceInMeters;
    }

    public String getFormattedDistance() {
        return DistanceFormatter.format(distanceInMeters);
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

    public int getRotationBearing() {
        return 360 - json.getInt(7);
    }

    public int getBearing() {
        return json.getInt(7);
    }

    public double[] getPoint() {
        return point;
    }

    public void setPoint(double[] point) {
        this.point = point;
    }

    private String getFullInstructionPattern() {
        String controllingGluePhrase = "and continue on for";
        String pattern = "%s %s "+ controllingGluePhrase + " %s";
        if (getHumanTurnInstruction().equals(HEAD_ON) ||
                getHumanTurnInstruction().equals(GO_STRAIGHT)) {
            controllingGluePhrase = "for";
            pattern = "%s %s "+ controllingGluePhrase + " %s";
        } else if (getHumanTurnInstruction().equals(REACHED_YOUR_DESTINATION)) {
            pattern = "%s %s";
        }
        return pattern;
    }

    public String getFullInstruction() {
        return String.format(Locale.US,
                getFullInstructionPattern(),
                getHumanTurnInstruction(),
                getName(),
                DistanceFormatter.format(distanceInMeters, true));
    }

    public String getSimpleInstruction() {
        return String.format(Locale.US, "%s %s", getHumanTurnInstruction(), getName());
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Instruction: (%.5f, %.5f) %s %s",
                point[0], point[1], getHumanTurnInstruction(), getName());
    }

    public double[] calculateMidpointToNext(double[] nextPoint) {
        double midLat = (point[0] + nextPoint[0]) / 2;
        double midLng = (point[1] + nextPoint[1]) / 2;
        double[] midPoint = {midLat, midLng};
        return midPoint;
    }

    public double[] snapTo(double[] location, int offset) {
        double lat1 = Math.toRadians(point[0]);
        double lon1 = Math.toRadians(point[1]);
        double lat2 = Math.toRadians(location[0]);
        double lon2 = Math.toRadians(location[1]);

        double brng13 = Math.toRadians(json.getInt(7));
        double brng23 = Math.toRadians(json.getInt(7) + offset);
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double dist12 = 2 * Math.asin(Math.sqrt(Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2)));
        if (dist12 == 0) {
            return null;
        }

        // initial/final bearings between points
        double brngA = Math.acos((Math.sin(lat2) - Math.sin(lat1) * Math.cos(dist12)) /
                (Math.sin(dist12) * Math.cos(lat1)));

        double brngB = Math.acos((Math.sin(lat1) - Math.sin(lat2) * Math.cos(dist12)) /
                (Math.sin(dist12) * Math.cos(lat2)));

        double brng12, brng21;
        if (Math.sin(lon2 - lon1) > 0) {
            brng12 = brngA;
            brng21 = 2 * Math.PI - brngB;
        } else {
            brng12 = 2 * Math.PI - brngA;
            brng21 = brngB;
        }

        double alpha1 = (brng13 - brng12 + Math.PI) % (2 * Math.PI) - Math.PI;  // angle 2-1-3
        double alpha2 = (brng21 - brng23 + Math.PI) % (2 * Math.PI) - Math.PI;  // angle 1-2-3

        if (Math.sin(alpha1) == 0 && Math.sin(alpha2) == 0) {
            return null;  // infinite intersections
        }
        if (Math.sin(alpha1) * Math.sin(alpha2) < 0) {
            return null;       // ambiguous intersection
        }

        double alpha3 = Math.acos(-Math.cos(alpha1) * Math.cos(alpha2) +
                Math.sin(alpha1) * Math.sin(alpha2) * Math.cos(dist12));
        double dist13 = Math.atan2(Math.sin(dist12) * Math.sin(alpha1) * Math.sin(alpha2),
                Math.cos(alpha2) + Math.cos(alpha1) * Math.cos(alpha3));
        double lat3 = Math.asin(Math.sin(lat1) * Math.cos(dist13) +
                Math.cos(lat1) * Math.sin(dist13) * Math.cos(brng13));
        double dLon13 = Math.atan2(Math.sin(brng13) * Math.sin(dist13) * Math.cos(lat1),
                Math.cos(dist13) - Math.sin(lat1) * Math.sin(lat3));
        double lon3 = ((lon1 + dLon13) + 3 * Math.PI) % (2 * Math.PI) - Math.PI;  // normalise to -180..+180º

        double[] point = {Math.toDegrees(lat3), Math.toDegrees(lon3)};
        return point;
    }
    private int parseTurnInstruction(JSONArray json) {
        String turn = json.getString(0);
        String[] split = turn.split("-");
        return Integer.valueOf(split[0]);
    }
}
