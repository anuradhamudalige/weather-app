package com.anuradha.weather;

import com.anuradha.weather.model.Weather;
import com.anuradha.weather.services.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
public class WeatherappApplicationTests {

    @Autowired
    MockMvc mockMvc;

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
    public void contextLoads() throws Exception {

        Mockito.when(weatherService.getAll()).thenReturn(mockWeatherList);
        Mockito.doNothing().when(weatherService).checkAndUpdate();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_XHTML_XML)
        ).andReturn();
    }

}
