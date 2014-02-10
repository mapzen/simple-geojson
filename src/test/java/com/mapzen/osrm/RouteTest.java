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
        //route = new Route(routeJson);
    }

    @Test
    public void isObject() throws Exception {
        assert(route != null);
    }

    @Test
    public void hasTotalDistance() throws Exception {
        assert(route.getTotalDistance() != 0);
    }

    @Test
    public void hasCorrectTotalDistance() throws Exception {
        assert(route.getTotalDistance() == 1721);
    }

    @Test
    public void hasTotalTime() throws Exception {
        assert(route.getTotalTime() != 0);
    }

    @Test
    public void hasCorrectTotalTime() throws Exception {
        assert(route.getTotalTime() == 128);
    }

    @Test
    public void hasRouteInstructions() throws Exception {
        ArrayList<Instruction> instructions = route.getRouteInstructions();
        assert(instructions.size() == 6);
    }

    @Test
    public void hasGeometry() throws Exception {
        assert(route.getGeometry() != null);
    }

    private Route getRoute(String name) throws Exception {
        String fileName = System.getProperty("user.dir");
        File file = new File(fileName + "/src/test/fixtures/" + name + ".route");
        String content = FileUtils.readFileToString(file, "UTF-8");
        return new Route(content);
    }

    @Test
    public void hasCorrectNumberOfInstructionsInBrooklyn() throws Exception {
        // TODO path to fixtures setup
        Route brooklynRoute = getRoute("brooklyn");
        assert(brooklynRoute.getRouteInstructions().size() == 6);
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
            assert(Double.compare(Math.ceil(instructionPoint[0]), Math.ceil(expectedPoint[0])) == 0);
            assert(Double.compare(Math.ceil(instructionPoint[1]), Math.ceil(expectedPoint[1])) == 0);
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
            assert(instructionDirection.equals(expectedDirection));
        }
    }

    @Test
    public void testHasRoute() throws Exception {
        assert(route.getStatus() == 0);
    }

    @Test
    public void testHasRouteMethod() throws Exception {
        assert(route.foundRoute());
    }

    @Test
    public void testHasNoRoute() throws Exception {
        route = getRoute("unsuccessful");
        assert(route.getStatus() == 207);
    }

    @Test
    public void testHasNoRouteMethod() throws Exception {
        route = getRoute("unsuccessful");
        assert(!route.foundRoute());
    }
}
