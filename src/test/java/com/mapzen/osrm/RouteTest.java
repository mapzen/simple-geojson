package com.mapzen.osrm;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

public class RouteTest {
    String routeJson =  "{\n" +
            "    \"alternative_geometries\": [],\n" +
            "    \"alternative_indices\": [],\n" +
            "    \"alternative_instructions\": [],\n" +
            "    \"alternative_names\": [\n" +
            "        [\n" +
            "            \"\",\n" +
            "            \"\"\n" +
            "        ]\n" +
            "    ],\n" +
            "    \"alternative_summaries\": [],\n" +
            "    \"hint_data\": {\n" +
            "        \"checksum\": 0,\n" +
            "        \"locations\": [\n" +
            "            \"yhZmAQqkAAAdAAAA____f13_oOjJA8Y_5G5sAiwDl_s\",\n" +
            "            \"WEtmAdIPAADHAAAAjAAAAEceNbcnvuI_NaZsAkw1l_s\"\n" +
            "        ]\n" +
            "    },\n" +
            // sample geometry taken from:
            // https://developers.google.com/maps/documentation/utilities/polylinealgorithm?csw=1
            // Points: (38.5, -120.2), (40.7, -120.95), (43.252, -126.453)
            "    \"route_geometry\": \"_p~iF~ps|U_ulLnnqC_mqNvxq`@\",\n" +
            "    \"route_instructions\": [\n" +
            "        [\n" +
            "            \"10\",\n" + // turn instruction
            "            \"19th Street\",\n" + // way
            "            160,\n" + // length in meters
            "            0,\n" + // position?
            "            0,\n" + // time in seconds
            "            \"160m\",\n" + // length with unit
            "            \"SE\",\n" + //earth direction
            "            128\n" + // azimuth
            "        ],\n" +
            "        [\n" +
            "            \"7\",\n" +
            "            \"7th Avenue\",\n" +
            "            1937,\n" +
            "            1,\n" +
            "            0,\n" +
            "            \"1937m\",\n" +
            "            \"NE\",\n" +
            "            38\n" +
            "        ],\n" +
            "        [\n" +
            "            \"7\",\n" +
            "            \"Union Street\",\n" +
            "            97,\n" +
            "            29,\n" +
            "            0,\n" +
            "            \"97m\",\n" +
            "            \"NW\",\n" +
            "            297\n" +
            "        ],\n" +
            "        [\n" +
            "            \"15\",\n" +
            "            \"\",\n" +
            "            0,\n" +
            "            30,\n" +
            "            0,\n" +
            "            \"\",\n" +
            "            \"N\",\n" +
            "            0.0\n" +
            "        ]\n" +
            "    ],\n" +
            "    \"route_name\": [\n" +
            "        \"19th Street\",\n" +
            "        \"7th Avenue\"\n" +
            "    ],\n" +
            "    \"route_summary\": {\n" +
            "        \"end_point\": \"Union Street\",\n" +
            "        \"start_point\": \"19th Street\",\n" +
            "        \"total_distance\": 2195,\n" +
            "        \"total_time\": 211\n" +
            "    },\n" +
            "    \"status\": 0,\n" +
            "    \"status_message\": \"Found route between points\",\n" +
            "    \"via_indices\": [\n" +
            "        0,\n" +
            "        30\n" +
            "    ],\n" +
            "    \"via_points\": [\n" +
            "        [\n" +
            "            40.660708,\n" +
            "            -73.989332\n" +
            "        ],\n" +
            "        [\n" +
            "            40.674869,\n" +
            "            -73.9765\n" +
            "        ]\n" +
            "    ]\n" +
            "}\n";

    private Route route;

    @Before
    public void setup() throws Exception {
        route = new Route(routeJson);
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
        assert(route.getTotalDistance() == 2195);
    }

    @Test
    public void hasTotalTime() throws Exception {
        assert(route.getTotalTime() != 0);
    }

    @Test
    public void hasCorrectTotalTime() throws Exception {
        assert(route.getTotalTime() == 211);
    }

    @Test
    public void hasRouteInstructions() throws Exception {
        ArrayList<Instruction> instructions = route.getRouteInstructions();
        assert(instructions.size() == 4);
    }

    @Test
    public void hasGeometry() throws Exception {
        assert(route.getGeometry() != null);
    }

    @Test
    public void hasDecodedGeometry() throws Exception {
        // sample geometry taken from:
        // https://developers.google.com/maps/documentation/utilities/polylinealgorithm?csw=1
        // Points: (38.5, -120.2), (40.7, -120.95), (43.252, -126.453)
        // persision is different so that's why the numbers are wonkey
        // "_p~iF~ps|U_ulLnnqC_mqNvxq`@\
        ArrayList<double[]> list = route.getGeometry();

        assert(list.get(0)[0] == 3.85);
        assert(list.get(0)[1] == -12.02);

        assert(list.get(1)[0] == 4.07);
        assert(list.get(1)[1] == -12.095);

        assert(list.get(2)[0] == 4.3252);
        assert(list.get(2)[1] == -12.6453);
    }

    private Route getBrooklynRoute() throws Exception {
        String fileName = System.getProperty("user.dir");
        File file = new File(fileName + "/src/test/fixtures/brooklyn.route");
        String content = FileUtils.readFileToString(file, "UTF-8");
        return new Route(content);
    }

    @Test
    public void hasCorrectNumberOfInstructionsInBrooklyn() throws Exception {
        // TODO path to fixtures setup
        Route brooklynRoute = getBrooklynRoute();
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
        Route brooklynRoute = getBrooklynRoute();

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
        points.add("Head On");
        points.add("Turn Right");
        points.add("Turn Right");
        points.add("Turn Right");
        points.add("Turn Right");
        points.add("Reached Your Destination");
        Route brooklynRoute = getBrooklynRoute();

        ListIterator<String> expectedPoints = points.listIterator();
        for(Instruction instruction: brooklynRoute.getRouteInstructions()) {
            String expectedDirection = expectedPoints.next();
            String instructionDirection = instruction.getHumanTurnInstruction();
            assert(instructionDirection.equals(expectedDirection));
        }
    }


}
