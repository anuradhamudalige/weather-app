package com.anuradha.weather.controllers;

import com.anuradha.weather.model.Weather;
import com.anuradha.weather.services.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;
    private List<Weather> mockWeatherList;

    @Before
    public void setUp() {
        mockWeatherList  = new ArrayList<>();
        mockWeatherList.add(new Weather());
        mockWeatherList.add(new Weather());
        mockWeatherList.get(0).setKey("ca-xe");
        mockWeatherList.get(1).setKey("tk-jp");
    }

    @Test
    public void showUpdate() throws Exception {
        Mockito.doNothing().when(weatherService).getUpdates();
        Mockito.when(weatherService.getAll()).thenReturn(mockWeatherList);
        Mockito.doNothing().when(weatherService).checkAndUpdate();
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(content().string(containsString("<!DOCTYPE html>")));
    }
}