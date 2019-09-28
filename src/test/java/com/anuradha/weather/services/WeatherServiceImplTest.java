package com.anuradha.weather.services;

import com.anuradha.weather.model.Weather;
import com.anuradha.weather.repository.WeatherArchiveRepository;
import com.anuradha.weather.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceImplTest {

    @MockBean
    WeatherRepository weatherRepository;

    @MockBean
    WeatherArchiveRepository weatherArchiveRepository;

    @MockBean
    MongoTemplate mongoTemplate;

    private WeatherServiceImpl weatherServiceImpl = new WeatherServiceImpl();

    @Before
    public void setUp() {

    }
    @Test
    public void create() throws IOException {
        Mockito.when(weatherRepository.save(any())).thenReturn(getWeatherObj());
        assertEquals(weatherServiceImpl.create("Campbell, CA", getJSON()).getKey(), "Campbell, CA");
        assertEquals(weatherServiceImpl.create("Campbell, CA", getJSON()).getTime(), 1569679245);
    }

    @Test
    public void getAll() {
    }

    @Test
    public void findByKey() {
    }

    @Test
    public void checkAndUpdate() {
    }

    @Test
    public void houseKeepData() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void archive() {
    }

    @Test
    public void getUpdates() {
    }

    private JsonNode getJSON() throws IOException {
        String template = "\"currently\":{\"time\":1569679245,\"summary\":\"Clear\",\"icon\":\"clear-night\",\"precipIntensity\":0,\"precipProbability\":0,\"temperature\":65.11,\"apparentTemperature\":65.31,\"dewPoint\":60.45,\"humidity\":0.85,\"pressure\":1018.4,\"windSpeed\":5.42,\"windGust\":6.18,\"windBearing\":195,\"cloudCover\":0,\"uvIndex\":0,\"visibility\":10,\"ozone\":291.8}";
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(template).get("currently");
    }

    private Weather getWeatherObj() {
        Weather templateObj = new Weather();
        templateObj.setKey("Campbell, CA");
        templateObj.setTime(1569679245);
        return  templateObj;
    }
}