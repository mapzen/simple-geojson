package com.mapzen.geo;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GeoFeatureTest {
    private GeoFeature blankGeoFeature;
    private JSONObject json;

    @Before
    public void setUp() throws Exception {
        blankGeoFeature = new GeoFeature();
        json = new JSONObject("{\"" +
                "type\":\"Feature\"," +
                "\"geometry\":" +
                "{\"type\":\"Point\"," +
                "\"coordinates\": [\"-0.12739091\",\"51.4993491\"]}," +
                "\"properties\":" +
                "{\"name\":\"Feature Name to Display\"," +
                "\"type\":\"testDescription\"," +
                "\"country_code\":\"testUS\"," +
                "\"country_name\":\"testUnited States\"," +
                "\"admin1_abbr\":\"testNY\"," +
                "\"admin1_name\":\"testNew York\"," +
                "\"marker-color\":\"#F00\"}}");
    }

    @Test
    public void isObject() throws Exception {
        assertThat(blankGeoFeature, notNullValue());
    }

    @Test
    public void hasGeometry() throws Exception {
        Geometry geometry = blankGeoFeature.getGeometry();
        assertThat(geometry, notNullValue());
    }

    @Test
    public void hasProperties() throws Exception {
        assertThat(blankGeoFeature.getProperties(), notNullValue());
    }

    @Test
    public void canSetProperties() throws Exception {
        String expected = "test_value";
        blankGeoFeature.setProperty("test_key", expected);
        String actual = blankGeoFeature.getProperty("test_key");
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void hasLat() throws Exception {
        assertTrue(blankGeoFeature.getLat() == 0);
    }

    @Test
    public void hasLot() throws Exception {
        assertTrue(blankGeoFeature.getLon() == 0);
    }

    @Test
    public void canSetLat() throws Exception {
        double expectedLat = 10.0;
        blankGeoFeature.setLat(expectedLat);
        double actual = blankGeoFeature.getLat();
        assertThat(actual, equalTo(expectedLat));
    }

    @Test
    public void canSetLot() throws Exception {
        double expectedLon = -10.0;
        blankGeoFeature.setLon(expectedLon);
        double actual = blankGeoFeature.getLon();
        assertThat(actual, equalTo(expectedLon));
    }

    private GeoFeature fromJson() throws Exception {
        GeoFeature geoFeature = new GeoFeature();
        geoFeature.buildFromJSON(json);
        return geoFeature;
    }

    @Test
    public void hasLonFromJson() throws Exception {
        assertThat(fromJson().getLon(), equalTo(-0.12739091));
    }

    @Test
    public void hasLatFromJson() throws Exception {
        assertThat(fromJson().getLat(), equalTo(51.4993491));
    }

    @Test
    public void hasTitleFromJson() throws Exception {
        assertThat(fromJson().getProperty("name"), equalTo("Feature Name to Display"));
    }

    @Test
    public void hasDescriptionFromJson() throws Exception {
        assertThat(fromJson().getProperty("type"), equalTo("testDescription"));
    }

    @Test
    public void hasCountryNameFromJson() throws Exception {
        assertThat(fromJson().getProperty("country_name"), equalTo("testUnited States"));
    }

    @Test
    public void hasCountryCodeFromJson() throws Exception {
        assertThat(fromJson().getProperty("country_code"), equalTo("testUS"));
    }

    @Test
    public void hasAdmin1AbbrFromJson() throws Exception {
        assertThat(fromJson().getProperty("admin1_abbr"), equalTo("testNY"));
    }

    @Test
    public void hasAdmin1NameFromJson() throws Exception {
        assertThat(fromJson().getProperty("admin1_name"), equalTo("testNew York"));
    }
}
