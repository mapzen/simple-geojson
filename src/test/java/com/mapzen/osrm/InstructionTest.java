package com.mapzen.osrm;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class InstructionTest {
    private Instruction instruction;
    @Before
    public void setup() throws Exception {
        String json = "        [\n" +
                "            \"10\",\n" +
                "            \"19th Street\",\n" +
                "            160,\n" +
                "            0,\n" +
                "            0,\n" +
                "            \"160m\",\n" +
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
        assert(instruction.getTurnInstruction() != null);
    }

    @Test
    public void hasCorrectTurnInstruction() throws Exception {
        assert(instruction.getTurnInstruction().equals("10"));
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
        assert(instruction.getDistance() == 160);
    }

    @Test
    public void hasDirection() throws Exception {
        assert(instruction.getDirection() != null);
    }

    @Test
    public void hasCorrectDirection() throws Exception {
        assert(instruction.getDirection().equals("SE"));
    }
}
