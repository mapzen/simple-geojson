package com.mapzen.geo;

import org.junit.Before;
import org.junit.Test;

public class FeatureTest {
    private Feature blankFeature;

    @Before
    public void setUp() throws Exception {
        blankFeature = new Feature();
    }

    @Test
    public void isObject() throws Exception {
        assert(blankFeature != null);
    }

    @Test
    public void hasGeometry() throws Exception {
        Geometry geometry = blankFeature.getGeometry();
        assert(geometry != null);
    }

    @Test
    public void hasProperties() throws Exception {
        assert(blankFeature.getProperties() != null);
    }

    @Test
    public void canSetProperties() throws Exception {
        String expected = "test_value";
        blankFeature.setProperty("test_key", expected);
        String actual = blankFeature.getProperty("test_key");
        assert(expected.equals(actual));
    }

    @Test
    public void hasLat() throws Exception {
        assert(blankFeature.getLat() == 0);
    }

    @Test
    public void hasLot() throws Exception {
        assert(blankFeature.getLon() == 0);
    }

    @Test
    public void canSetLat() throws Exception {
        double expectedLat = 10.0;
        blankFeature.setLat(expectedLat);
        double actual = blankFeature.getLat();
        assert(expectedLat == actual);
    }

    @Test
    public void canSetLot() throws Exception {
        double expectedLon = -10.0;
        blankFeature.setLon(expectedLon);
        double actual = blankFeature.getLon();
        assert(expectedLon == actual);
    }
}
