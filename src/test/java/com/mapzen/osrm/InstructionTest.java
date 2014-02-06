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
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(NoTurn);
    }

    @Test
    public void turnInstructionHasGoStraight() {
        instruction.setTurnInstruction(1);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(GoStraight);
    }

    @Test
    public void turnInstructionHasTurnSlightRight() {
        instruction.setTurnInstruction(2);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnSlightRight);
    }

    @Test
    public void turnInstructionHasTurnRight() {
        instruction.setTurnInstruction(3);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnRight);
    }

    @Test
    public void turnInstructionHasTurnSharpRight() {
        instruction.setTurnInstruction(4);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnSharpRight);
    }

    @Test
    public void turnInstructionHasUTurn() {
        instruction.setTurnInstruction(5);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(UTurn);
    }

    @Test
    public void turnInstructionHasTurnSharpLeft() {
        instruction.setTurnInstruction(6);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnSharpLeft);
    }

    @Test
    public void turnInstructionHasTurnLeft() {
        instruction.setTurnInstruction(7);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnLeft);
    }

    @Test
    public void turnInstructionHasTurnSlightLeft() {
        instruction.setTurnInstruction(8);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(TurnSlightLeft);
    }

    @Test
    public void turnInstructionHasReachViaPoint() {
        instruction.setTurnInstruction(9);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(ReachViaPoint);
    }

    @Test
    public void turnInstructionHasHeadOn() {
        instruction.setTurnInstruction(10);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(HeadOn);
    }

    @Test
    public void turnInstructionHasEnterRoundAbout() {
        instruction.setTurnInstruction(11);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(EnterRoundAbout);
    }

    @Test
    public void turnInstructionHasLeaveRoundAbout() {
        instruction.setTurnInstruction(12);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(LeaveRoundAbout);
    }

    @Test
    public void turnInstructionHasStayOnRoundAbout() {
        instruction.setTurnInstruction(13);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(StayOnRoundAbout);
    }

    @Test
    public void turnInstructionHasStartAtEndOfStreet() {
        instruction.setTurnInstruction(14);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(StartAtEndOfStreet);
    }

    @Test
    public void turnInstructionHasReachedYourDestination() {
        instruction.setTurnInstruction(15);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(ReachedYourDestination);
    }

    @Test
    public void turnInstructionHasEnterAgainstAllowedDirection() {
        instruction.setTurnInstruction(16);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(EnterAgainstAllowedDirection);
    }

    @Test
    public void turnInstructionHasLeaveAgainstAllowedDirection() {
        instruction.setTurnInstruction(17);
        assertThat(instruction.getHumanTurnInstruction()).isEqualTo(LeaveAgainstAllowedDirection);
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
    public void hasBearingAngle() throws Exception {
        String json = "[10,\"\", 1609,0,0,\"1609m\",\"SE\",\"128\"]";
        JSONArray jsonArray = new JSONArray(json);
        instruction = new Instruction(jsonArray);
        assertThat(instruction.getBearing()).isEqualTo(360 - 128);
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
}
