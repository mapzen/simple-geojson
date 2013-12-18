package com.mapzen.geo;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class FeatureTest {
    private Feature blankFeature;
    private JSONObject json;

    @Before
    public void setUp() throws Exception {
        blankFeature = new Feature();
        json = new JSONObject("{\"" +
                "type\":\"Feature\"," +
                "\"geometry\":" +
                "{\"type\":\"Point\"," +
                "\"coordinates\": [-0.12739091,51.4993491]}," +
                "\"properties\":" +
                "{\"title\":\"Feature Name to Display\"," +
                "\"description\":\"testDescription\"," +
                "\"country_code\":\"testUS\"," +
                "\"country_name\":\"testUnited States\"," +
                "\"admin1_abbr\":\"testNY\"," +
                "\"admin1_name\":\"testNew York\"," +
                "\"type\":\"geoname\"," +
                "\"marker-color\":\"#F00\"}}");
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

    private Feature fromJson() throws Exception {
        Feature feature = new Feature();
        feature.buildFromJSON(json);
        return feature;
    }

    @Test
    public void hasLonFromJson() throws Exception {
        assert(fromJson().getLon() == -0.12739091);
    }

    @Test
    public void hasLatFromJson() throws Exception {
        assert(fromJson().getLat() == 51.4993491);
    }

    @Test
    public void hasTitleFromJson() throws Exception {
        assert( fromJson().getProperty("title").equals("Feature Name to Display"));
    }

    @Test
    public void hasDescriptionFromJson() throws Exception {
        assert( fromJson().getProperty("description").equals("testDescription"));
    }

    @Test
    public void hasCountryNameFromJson() throws Exception {
        assert( fromJson().getProperty("country_name").equals("testUnited States"));
    }

    @Test
    public void hasCountryCodeFromJson() throws Exception {
        assert( fromJson().getProperty("country_code").equals("testUS"));
    }

    @Test
    public void hasAdmin1AbbrFromJson() throws Exception {
        assert( fromJson().getProperty("admin1_abbr").equals("testNY"));
    }

    @Test
    public void hasAdmin1NameFromJson() throws Exception {
        assert( fromJson().getProperty("admin1_name").equals("testNew York"));
    }
}
