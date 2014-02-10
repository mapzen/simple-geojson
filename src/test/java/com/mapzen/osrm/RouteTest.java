package com.mapzen.osrm;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import static org.fest.assertions.api.Assertions.assertThat;

public class RouteTest {
    private Route route;

    @Before
    public void setup() throws Exception {
        route = getRoute("brooklyn");
    }

    @Test
    public void isObject() throws Exception {
        assertThat(route).isNotNull();
    }

    @Test
    public void hasTotalDistance() throws Exception {
        assertThat(route.getTotalDistance()).isNotEqualTo(0);
    }

    @Test
    public void hasCorrectTotalDistance() throws Exception {
        assertThat(route.getTotalDistance()).isEqualTo(1721);
    }

    @Test
    public void hasTotalTime() throws Exception {
        assertThat(route.getTotalTime()).isNotEqualTo(0);
    }

    @Test
    public void hasCorrectTotalTime() throws Exception {
        assertThat(route.getTotalTime()).isEqualTo(128);
    }

    @Test
    public void hasRouteInstructions() throws Exception {
        ArrayList<Instruction> instructions = route.getRouteInstructions();
        assertThat(instructions).hasSize(6);
    }

    @Test
    public void hasGeometry() throws Exception {
        assertThat(route.getGeometry()).isNotNull();
    }

    @Test
    public void hasCorrectNumberOfInstructionsInBrooklyn() throws Exception {
        // TODO path to fixtures setup
        Route brooklynRoute = getRoute("brooklyn");
        assertThat(brooklynRoute.getRouteInstructions()).hasSize(6);
    }

    @Test
    public void hasCorrectTurnByTurnCordinatesInBrooklyn() throws Exception {
        ArrayList<double[]> points = new ArrayList<double[]>();
        points.add(new double[]{40.66071, -73.98933});
        points.add(new double[]{40.65982, -73.98784});
        points.add(new double[]{40.65925, -73.98843});
        points.add(new double[]{40.66325, -73.99504});
        points.add(new double[]{40.66732, -73.99117});
        points.add(new double[]{40.66631, -73.98909});
        Route brooklynRoute = getRoute("brooklyn");

        ListIterator<double[]> expectedPoints = points.listIterator();
        for(Instruction instruction: brooklynRoute.getRouteInstructions()) {
            double[] expectedPoint = expectedPoints.next();
            double[] instructionPoint = instruction.getPoint();

            // ceiling it as the percision of the double is not identical on the sixth digit
            assertThat(Math.ceil(instructionPoint[0])).isEqualTo(Math.ceil(expectedPoint[0]));
            assertThat(Math.ceil(instructionPoint[1])).isEqualTo(Math.ceil(expectedPoint[1]));
        }
    }

    @Test
    public void hasCorrectTurnByTurnHumanInstructionsInBrooklyn() throws Exception {
        ArrayList<String> points = new ArrayList<String>();
        points.add("Head on");
        points.add("Make a right on to");
        points.add("Make a right on to");
        points.add("Make a right on to");
        points.add("Make a right on to");
        points.add("You have reached your destination");
        Route brooklynRoute = getRoute("brooklyn");

        ListIterator<String> expectedPoints = points.listIterator();
        for(Instruction instruction: brooklynRoute.getRouteInstructions()) {
            String expectedDirection = expectedPoints.next();
            String instructionDirection = instruction.getHumanTurnInstruction();
            assertThat(instructionDirection).isEqualTo(expectedDirection);
        }
    }

    @Test
    public void testHasRoute() throws Exception {
        assertThat(route.getStatus()).isEqualTo(0);
    }

    @Test
    public void testHasRouteMethod() throws Exception {
        assertThat(route.foundRoute()).isTrue();
    }

    @Test
    public void testHasNoRoute() throws Exception {
        route = getRoute("unsuccessful");
        assertThat(route.getStatus()).isEqualTo(207);
    }

    @Test
    public void testHasNoRouteMethod() throws Exception {
        route = getRoute("unsuccessful");
        assertThat(route.foundRoute()).isFalse();
    }

    @Test
    public void shouldHaveStartCoordinates() throws Exception {
        route = getRoute("brooklyn");
        double[] expected = {
            40.660708,
            -73.989332
        };
        assertThat(route.getStartCoordinates()).isEqualTo(expected);
    }

    private Route getRoute(String name) throws Exception {
        String fileName = System.getProperty("user.dir");
        File file = new File(fileName + "/src/test/fixtures/" + name + ".route");
        String content = FileUtils.readFileToString(file, "UTF-8");
        return new Route(content);
    }

}
