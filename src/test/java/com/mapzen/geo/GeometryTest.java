package com.mapzen.geo;

import org.junit.Before;
import org.junit.Test;

public class GeometryTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void isObject() throws Exception {
        Geometry geometry = new Geometry();
        assert(geometry != null);
    }

    @Test
    public void hasType() throws Exception {
        Geometry geometry = new Geometry();
        assert(geometry.getType() != null);
    }

    @Test
    public void hasHardCodedPoint() throws Exception {
        Geometry geometry = new Geometry();
        assert("Point" == geometry.getType());
    }

    @Test
    public void hasDefaultLat() throws Exception {
        Geometry geometry = new Geometry();
        assert(geometry.getLat() == 0);
    }

    @Test
    public void hasDefaultLon() throws Exception {
        Geometry geometry = new Geometry();
        assert(geometry.getLon() == 0);
    }

    @Test
    public void canSetLat() throws Exception {
        Geometry geometry = new Geometry();
        double expected = 10.1;
        geometry.setLat(expected);
        assert(expected == geometry.getLat());
    }

    @Test
    public void canSetLon() throws Exception {
        Geometry geometry = new Geometry();
        double expected = 10.1;
        geometry.setLon(expected);
        assert(expected == geometry.getLon());
    }
}
