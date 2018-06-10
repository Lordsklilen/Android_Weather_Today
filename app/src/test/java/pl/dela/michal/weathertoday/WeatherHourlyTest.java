package pl.dela.michal.weathertoday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeatherHourlyTest {
    WeatherHourly weatherHourly;
    @BeforeEach
    void setUp() {
        weatherHourly = new WeatherHourly("datadatadatadatadatadata","temperatura", "link", "Icon", "IconPhase","PrecipitationProbability");
    }

    @Test
    void getDate() {
        assertEquals("datadatadatadatadatadata".substring(5,10),weatherHourly.getDate());
    }

    @Test
    void getHour() {
        assertEquals("datadatadatadatadatadata".substring(11,16),weatherHourly.getHour());
    }

    @Test
    void getIconPhrase() {
        assertEquals("IconPhase",weatherHourly.getIconPhrase());
    }

    @Test
    void getTemperature() {
        assertEquals("temperatura",weatherHourly.getTemperature());

    }
}