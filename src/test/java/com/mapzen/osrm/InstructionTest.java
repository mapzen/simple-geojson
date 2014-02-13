package com.mapzen.osrm;

import com.mapzen.geo.DistanceFormatter;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;

import static com.mapzen.osrm.Instruction.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
        assertThat(instruction).isNotNull();
    }

    @Test
    public void hasTurnInstruction() throws Exception {
        assertThat(instruction.getTurnInstruction()).isNotEqualTo(0);
    }

    @Test
    public void hasCorrectTurnInstruction() throws Exception {
        assertThat(instruction.getTurnInstruction()).isNotEqualTo(0);
    }

    @Test
    public void turnInstructionHasNoTurn() {
        instruction.setTurnInstruction(0);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(NO_TURN);
    }

    @Test
    public void turnInstructionHasGoStraight() {
        instruction.setTurnInstruction(1);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(GO_STRAIGHT);
    }

    @Test
    public void turnInstructionHasTurnSlightRight() {
        instruction.setTurnInstruction(2);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_SLIGHT_RIGHT);
    }

    @Test
    public void turnInstructionHasTurnRight() {
        instruction.setTurnInstruction(3);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_RIGHT);
    }

    @Test
    public void turnInstructionHasTurnSharpRight() {
        instruction.setTurnInstruction(4);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_SHARP_RIGHT);
    }

    @Test
    public void turnInstructionHasUTurn() {
        instruction.setTurnInstruction(5);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(U_TURN);
    }

    @Test
    public void turnInstructionHasTurnSharpLeft() {
        instruction.setTurnInstruction(6);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_SHARP_LEFT);
    }

    @Test
    public void turnInstructionHasTurnLeft() {
        instruction.setTurnInstruction(7);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_LEFT);
    }

    @Test
    public void turnInstructionHasTurnSlightLeft() {
        instruction.setTurnInstruction(8);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TURN_SLIGHT_LEFT);
    }

    @Test
    public void turnInstructionHasReachViaPoint() {
        instruction.setTurnInstruction(9);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(REACH_VIA_POINT);
    }

    @Test
    public void turnInstructionHasHeadOn() {
        instruction.setTurnInstruction(10);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(HEAD_ON);
    }

    @Test
    public void turnInstructionHasEnterRoundAbout() {
        instruction.setTurnInstruction(11);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(ENTER_ROUND_ABOUT);
    }

    @Test
    public void turnInstructionHasLeaveRoundAbout() {
        instruction.setTurnInstruction(12);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(LEAVE_ROUND_ABOUT);
    }

    @Test
    public void turnInstructionHasStayOnRoundAbout() {
        instruction.setTurnInstruction(13);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(STAY_ON_ROUND_ABOUT);
    }

    @Test
    public void turnInstructionHasStartAtEndOfStreet() {
        instruction.setTurnInstruction(14);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(START_AT_END_OF_STREET);
    }

    @Test
    public void turnInstructionHasReachedYourDestination() {
        instruction.setTurnInstruction(15);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(REACHED_YOUR_DESTINATION);
    }

    @Test
    public void turnInstructionHasEnterAgainstAllowedDirection() {
        instruction.setTurnInstruction(16);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(ENTER_AGAINST_ALLOWED_DIRECTION);
    }

    @Test
    public void turnInstructionHasLeaveAgainstAllowedDirection() {
        instruction.setTurnInstruction(17);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(LEAVE_AGAINST_ALLOWED_DIRECTION);
    }

    @Test
    public void hasName() throws Exception {
        assertThat(instruction.getName()).isNotNull();
    }

    @Test
    public void hasCorrectName() throws Exception {
        assertThat(instruction.getName()).isEqualTo("19th Street");
    }

    @Test
    public void hasDistance() throws Exception {
        assertThat(instruction.getDistance()).isGreaterThan(-1);
    }

    @Test
    public void hasCorrectDistance() throws Exception {
        assertThat(instruction.getDistance()).isEqualTo(1609);
    }

    @Test
    public void hasDirection() throws Exception {
        assertThat(instruction.getDirection()).isNotNull();
    }

    @Test
    public void hasCorrectDirection() throws Exception {
        assertThat(instruction.getDirection()).isEqualTo("SE");
    }

    @Test
    public void hasNdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("N");
        assertThat(i.getDirectionAngle()).isEqualTo(0f);
    }

    @Test
    public void hasNEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("NE");
        assertThat(i.getDirectionAngle()).isEqualTo(315f);
    }

    @Test
    public void hasEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("E");
        assertThat(i.getDirectionAngle()).isEqualTo(270f);
    }

    @Test
    public void hasSEdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("SE");
        assertThat(i.getDirectionAngle()).isEqualTo(225f);
    }

    @Test
    public void hasSdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("S");
        assertThat(i.getDirectionAngle()).isEqualTo(180f);
    }

    @Test
    public void hasSWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("SW");
        assertThat(i.getDirectionAngle()).isEqualTo(135f);
    }

    @Test
    public void hasWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("W");
        assertThat(i.getDirectionAngle()).isEqualTo(90f);
    }

    @Test
    public void hasNWdirectionAngle() throws Exception {
        Instruction i = getInstructionWithDirection("NW");
        assertThat(i.getDirectionAngle()).isEqualTo(45f);
    }

    @Test
    public void hasDirectionAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assertThat(instruction.getDirection()).isEqualTo("SE");
    }

    @Test
    public void hasRotationBearingAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assertThat(instruction.getRotationBearing()).isEqualTo(360 - 128);
    }

    @Test
    public void hasBearingAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assertThat(instruction.getBearing()).isEqualTo(128);
    }

    @Test
    public void hasPointCoordinates() throws Exception {
        assertThat(instruction.getPoint()).isNotNull();
    }

    @Test
    public void canSetCoordinates() throws Exception {
        double[] expected = {3.3, 4.4};
        instruction.setPoint(expected);
        assertThat(instruction.getPoint()).isEqualTo(expected);

    }

    @Test
    public void testHeadOnFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(HEAD_ON);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s for %s"), actual);
    }

    @Test
    public void testGoStraightFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(GO_STRAIGHT);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s for %s"), actual);
    }

    @Test
    public void testReachedYourDestinationFullInstruction() throws Exception {
        Instruction currentInstruction = getInstructionWithTurn(REACHED_YOUR_DESTINATION);
        String actual = currentInstruction.getFullInstruction();
        assertEquals(getExpectedFullInstructionFor(currentInstruction,
                "%s %s"), actual);
    }

    @Test
    public void testOtherFullInstruction() throws Exception {
        Instruction currentInstruction;
        String actual;
        for(int i = 0; i < decodedInstructions.length; i++) {
           if (!decodedInstructions[i].equals(REACHED_YOUR_DESTINATION) &&
                   !decodedInstructions[i].equals(GO_STRAIGHT) &&
                       !decodedInstructions[i].equals(HEAD_ON)) {
               currentInstruction = getInstructionWithTurn(decodedInstructions[i]);
               actual = currentInstruction.getFullInstruction();
               assertEquals(getExpectedFullInstructionFor(currentInstruction,
                       "%s %s and continue on for %s"), actual);
           }
        }
    }

    @Test
    public void shouldCalculateMidpointToNext() throws Exception {
        double[] currentPoint = {1,2};
        instruction.setPoint(currentPoint);
        double lat2 = 30;
        double lng2 = 50;
        double[] nextPoint = {lat2, lng2};
        double[] midPoint = instruction.calculateMidpointToNext(nextPoint);
        double[] point = instruction.getPoint();
        double lat1 = point[0];
        double lng1 = point[1];
        double[] expectedMidpoint = {((lat1+lat2)/2), ((lng1+lng2)/2) };
        assertThat(midPoint).isEqualTo(expectedMidpoint);
    }

    @Test
    public void testSimpleInstruction() throws Exception {
        assertThat(instruction.getSimpleInstruction()).isEqualTo("Head on 19th Street");
    }

    @Test
    public void getFormattedDistance_shouldReturnListViewDistance() throws Exception {
        instruction.setDistance(1);
        assertThat(instruction.getFormattedDistance()).isEqualTo("3 ft");
    }

    @Test
    public void getFullInstruction_shouldReturnNavigationDistance() throws Exception {
        instruction.setDistance(1);
        assertThat(instruction.getFullInstruction()).contains("now");
    }

    // Helper methods.

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
                DistanceFormatter.format(currentInstruction.getDistance(), true));
    }

    private Instruction getInstructionWithDirection(String dir) {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"" + dir + "\",\"128\"]";
        return new Instruction(new JSONArray(json));
    }

    @Test
    public void shouldSnapTo() throws Exception {
        double[] currentPoint = {40.660514,-73.989501};
        double[] referencePoint = {40.661139,-73.990051};
        instruction.setPoint(referencePoint);
        double[] onRoad = instruction.snapTo(currentPoint, -90);
        double lat = onRoad[0];
        double lon = onRoad[1];
        assertThat(lat).isEqualTo(40.660699685769025);
        assertThat(lon).isEqualTo(-73.98930975653286);
    }
}
