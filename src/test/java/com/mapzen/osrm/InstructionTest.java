package com.mapzen.osrm;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

import static com.mapzen.osrm.Instruction.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class InstructionTest {
    private Instruction instruction;
    @Before
    public void setup() throws Exception {
        String json = "        [\n" +
                "            \"10\",\n" +
                "            \"19th Street\",\n" +
                "            1609,\n" +
                "            0,\n" +
                "            0,\n" +
                "            \"1609m\",\n" +
                "            \"SE\",\n" +
                "            128\n" +
                "        ]\n";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
    }

    @Test
    public void isObject() throws Exception {
        assert(instruction != null);
    }

    @Test
    public void hasTurnInstruction() throws Exception {
        assert(instruction.getTurnInstruction() != 0);
    }

    @Test
    public void hasCorrectTurnInstruction() throws Exception {
        assert(instruction.getTurnInstruction() != 0);
    }

    @Test
    public void turnInstructionHasNoTurn() {
        instruction.setTurnInstruction(0);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.NoTurn));
    }

    @Test
    public void turnInstructionHasGoStraight() {
        instruction.setTurnInstruction(1);
        assert(instruction.getHumanTurnInstruction().equals(GoStraight));
    }

    @Test
    public void turnInstructionHasTurnSlightRight() {
        instruction.setTurnInstruction(2);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnSlightRight));
    }

    @Test
    public void turnInstructionHasTurnRight() {
        instruction.setTurnInstruction(3);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnRight));
    }

    @Test
    public void turnInstructionHasTurnSharpRight() {
        instruction.setTurnInstruction(4);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnSharpRight));
    }

    @Test
    public void turnInstructionHasUTurn() {
        instruction.setTurnInstruction(5);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.UTurn));
    }

    @Test
    public void turnInstructionHasTurnSharpLeft() {
        instruction.setTurnInstruction(6);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnSharpLeft));
    }

    @Test
    public void turnInstructionHasTurnLeft() {
        instruction.setTurnInstruction(7);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnLeft));
    }

    @Test
    public void turnInstructionHasTurnSlightLeft() {
        instruction.setTurnInstruction(8);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.TurnSlightLeft));
    }

    @Test
    public void turnInstructionHasReachViaPoint() {
        instruction.setTurnInstruction(9);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.ReachViaPoint));
    }

    @Test
    public void turnInstructionHasHeadOn() {
        instruction.setTurnInstruction(10);
        assert(instruction.getHumanTurnInstruction().equals(HeadOn));
    }

    @Test
    public void turnInstructionHasEnterRoundAbout() {
        instruction.setTurnInstruction(11);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.EnterRoundAbout));
    }

    @Test
    public void turnInstructionHasLeaveRoundAbout() {
        instruction.setTurnInstruction(12);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.LeaveRoundAbout));
    }

    @Test
    public void turnInstructionHasStayOnRoundAbout() {
        instruction.setTurnInstruction(13);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.StayOnRoundAbout));
    }

    @Test
    public void turnInstructionHasStartAtEndOfStreet() {
        instruction.setTurnInstruction(14);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.StartAtEndOfStreet));
    }

    @Test
    public void turnInstructionHasReachedYourDestination() {
        instruction.setTurnInstruction(15);
        assert(instruction.getHumanTurnInstruction().equals(ReachedYourDestination));
    }

    @Test
    public void turnInstructionHasEnterAgainstAllowedDirection() {
        instruction.setTurnInstruction(16);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.EnterAgainstAllowedDirection));
    }

    @Test
    public void turnInstructionHasLeaveAgainstAllowedDirection() {
        instruction.setTurnInstruction(17);
        assert(instruction.getHumanTurnInstruction().equals(Instruction.LeaveAgainstAllowedDirection));
    }

    @Test
    public void hasName() throws Exception {
        assert(instruction.getName() != null);
    }

    @Test
    public void hasCorrectName() throws Exception {
        assert(instruction.getName().equals("19th Street"));
    }

    @Test
    public void hasDistance() throws Exception {
        assert(instruction.getDistance() > -1);
    }

    @Test
    public void hasCorrectDistance() throws Exception {
        assert(instruction.getDistance() == 1609);
    }

    @Test
    public void hasDistanceInMiles() throws Exception {
        assert(instruction.getDistanceInMiles() == 1);
        instruction.setDistance(1609 * 4);
        assert(instruction.getDistanceInMiles() == 4);
    }

    @Test
    public void hasDirection() throws Exception {
        assert(instruction.getDirection() != null);
    }

    @Test
    public void hasCorrectDirection() throws Exception {
        assert(instruction.getDirection().equals("SE"));
    }

    private Instruction getInstructionWithDirection(String dir) {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"" + dir + "\",\"128\"]";
        return new Instruction(new JSONArray(json));
    }

    @Test
    public void hasNdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("N");
        assert(Float.compare(i.getDirectionAngle(), 0.0f) == 0);
    }

    @Test
    public void hasNEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("NE");
        assert(Float.compare(i.getDirectionAngle(), 315.0f) == 0);
    }

    @Test
    public void hasEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("E");
        assert(Float.compare(i.getDirectionAngle(), 270.0f) == 0);
    }

    @Test
    public void hasSEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("SE");
        assert(Float.compare(i.getDirectionAngle(), 225.0f) == 0);
    }

    @Test
    public void hasSdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("S");
        assert(Float.compare(i.getDirectionAngle(), 180.0f) == 0);
    }

    @Test
    public void hasSWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("SW");
        assert(Float.compare(i.getDirectionAngle(), 135.0f) == 0);
    }

    @Test
    public void hasWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("W");
        assert(Float.compare(i.getDirectionAngle(), 90.0f) == 0);
    }

    @Test
    public void hasNWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("NW");
        assert(Float.compare(i.getDirectionAngle(), 45.0f) == 0);
    }

    @Test
    public void hasDirectionAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assert(instruction.getDirection().equals("SE"));
    }

    @Test
    public void hasBearingAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assert(instruction.getBearing() == (360 - 128));
    }

    @Test
    public void hasPointCoordinates() throws Exception {
        assert(instruction.getPoint() != null);
    }

    @Test
    public void canSetCoordinates() throws Exception {
        double[] expected = {3.3, 4.4};
        instruction.setPoint(expected);
        assert(instruction.getPoint() == expected);

    }

    public void setDistanceInMiles(double miles) {
        int meters = (int) Math.round(Instruction.METERS_IN_MILE * miles);
        instruction.setDistance(meters);
    }

    @Test
    public void testThreeQuartersApproximationDistance() throws Exception {
        setDistanceInMiles(0.80);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "about \u00BE of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testThreeQuartersApproximationDistanceOverMile() throws Exception {
        setDistanceInMiles(3.80);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "3 miles and about \u00BE of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutHalfApproximationDistance() throws Exception {
        setDistanceInMiles(0.73);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "about \u00BD a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutHalfApproximationDistanceOverMile() throws Exception {
        setDistanceInMiles(3.73);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "3 miles and about \u00BD a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutOneQuarterOfAApproximationDistance() throws Exception {
        setDistanceInMiles(0.45);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "about \u00BC of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutOneQuarterOfAApproximationDistanceOverMile() throws Exception {
        setDistanceInMiles(3.45);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "3 miles and about \u00BC of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutOneEightOfAApproximationDistance() throws Exception {
        setDistanceInMiles(0.130);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "about \u215B of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testAboutOneEightOfAApproximationDistanceOverMile() throws Exception {
        setDistanceInMiles(3.130);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        String expected = "3 miles and about \u215B of a mile";
        assertEquals(expected, actual);
    }

    @Test
    public void testFeetsApproximationDistance() throws Exception {
        setDistanceInMiles(0.10000);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        int feet = (int) Math.round(0.10000 * Instruction.FEET_IN_MILE);
        String expected = String.valueOf(feet) + " feet";
        assertEquals(expected, actual);
    }

    @Test
    public void testFeetsApproximationDistanceOverMile() throws Exception {
        setDistanceInMiles(3.10000);
        String actual = instruction.getHumanDistance(Locale.ENGLISH);
        int feet = (int) Math.round(0.10000 * Instruction.FEET_IN_MILE);
        String expected = "3 miles and " + String.valueOf(feet) + " feet";
        assertEquals(expected, actual);
    }

    private Instruction getInstructionWithTurn(String turn) {
        ArrayList<String> withIndex = new ArrayList<String>(decodedInstructions.length);
        for(int i = 0; i < decodedInstructions.length; i++) {
            withIndex.add(decodedInstructions[i]);
        }
        instruction.setTurnInstruction(withIndex.indexOf(turn));
        return instruction;
    }

    private String getExpectedFullInstructionFor(Instruction currentInstruction, String pattern) {
        return String.format(Locale.ENGLISH, pattern,
                currentInstruction.getHumanTurnInstruction(),
                currentInstruction.getName(),
                currentInstruction.getHumanDistance(Locale.ENGLISH));
    }

    @Test
    public void testHeadOnFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(HeadOn);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s for %s"), actual);
    }

    @Test
    public void testGoStraightFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(GoStraight);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s for %s"), actual);
    }

    @Test
    public void testReachedYourDestinationFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(ReachedYourDestination);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s"), actual);
    }

    @Test
    public void testOtherFullInstruction() throws Exception {
        Instruction currentInstruction;
        String actual;
        for(int i = 0; i < decodedInstructions.length; i++) {
           if (!decodedInstructions[i].equals(ReachedYourDestination) &&
                   !decodedInstructions[i].equals(GoStraight) &&
                       !decodedInstructions[i].equals(HeadOn)) {
               currentInstruction = getInstructionWithTurn(decodedInstructions[i]);
               actual = currentInstruction.getFullInstruction();
               assertEquals(getExpectedFullInstructionFor(currentInstruction,
                       "%s %s and continue on for %s"), actual);
           }
        }
    }
}
