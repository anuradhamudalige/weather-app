package com.anuradha.weather.services;

import com.anuradha.weather.model.Weather;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface WeatherService {
    void getUpdates();
    Weather create(String key, JsonNode node);
    List<Weather> getAll();
    Weather findByKey(String key);
    void checkAndUpdate();
    void houseKeepData();
}
