package com.mapzen.geo;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class GeoFeatureTest {
    private GeoFeature blankGeoFeature;
    private JSONObject json;

    @Before
    public void setUp() throws Exception {
        blankGeoFeature = new GeoFeature();
        json = new JSONObject("{\"" +
                "type\":\"GeoFeature\"," +
                "\"geometry\":" +
                "{\"type\":\"Point\"," +
                "\"coordinates\": [\"-0.12739091\",\"51.4993491\"]}," +
                "\"properties\":" +
                "{\"name\":\"GeoFeature Name to Display\"," +
                "\"type\":\"testDescription\"," +
                "\"country_code\":\"testUS\"," +
                "\"country_name\":\"testUnited States\"," +
                "\"admin1_abbr\":\"testNY\"," +
                "\"admin1_name\":\"testNew York\"," +
                "\"marker-color\":\"#F00\"}}");
    }

    @Test
    public void isObject() throws Exception {
        assert(blankGeoFeature != null);
    }

    @Test
    public void hasGeometry() throws Exception {
        Geometry geometry = blankGeoFeature.getGeometry();
        assert(geometry != null);
    }

    @Test
    public void hasProperties() throws Exception {
        assert(blankGeoFeature.getProperties() != null);
    }

    @Test
    public void canSetProperties() throws Exception {
        String expected = "test_value";
        blankGeoFeature.setProperty("test_key", expected);
        String actual = blankGeoFeature.getProperty("test_key");
        assert(expected.equals(actual));
    }

    @Test
    public void hasLat() throws Exception {
        assert(blankGeoFeature.getLat() == 0);
    }

    @Test
    public void hasLot() throws Exception {
        assert(blankGeoFeature.getLon() == 0);
    }

    @Test
    public void canSetLat() throws Exception {
        double expectedLat = 10.0;
        blankGeoFeature.setLat(expectedLat);
        double actual = blankGeoFeature.getLat();
        assert(expectedLat == actual);
    }

    @Test
    public void canSetLot() throws Exception {
        double expectedLon = -10.0;
        blankGeoFeature.setLon(expectedLon);
        double actual = blankGeoFeature.getLon();
        assert(expectedLon == actual);
    }

    private GeoFeature fromJson() throws Exception {
        GeoFeature geoFeature = new GeoFeature();
        geoFeature.buildFromJSON(json);
        return geoFeature;
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
        assert( fromJson().getProperty("name").equals("GeoFeature Name to Display"));
    }

    @Test
    public void hasDescriptionFromJson() throws Exception {
        assert( fromJson().getProperty("type").equals("testDescription"));
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
