package com.anuradha.weather.controllers;

import com.anuradha.weather.model.Weather;
import com.anuradha.weather.services.WeatherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class WeatherController {
    private WeatherService weatherService;
    private static final Logger logger = LogManager.getLogger(WeatherController.class);
    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping({"/", ""})
    public String showUpdate(Model model) {
        this.weatherService.checkAndUpdate();
        List<Weather> weatherList = this.weatherService.getAll();
        model.addAttribute("updates", weatherList);
        logger.info(this.weatherService.getAll());
        return "index";
    }
}
