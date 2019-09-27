package com.anuradha.weather.services;

import com.anuradha.weather.model.Weather;
import com.anuradha.weather.model.WeatherArchive;
import com.anuradha.weather.repository.WeatherArchiveRepository;
import com.anuradha.weather.repository.WeatherRepository;
import com.anuradha.weather.utils.ConnectionParams;
import com.anuradha.weather.utils.Constants;
import com.anuradha.weather.utils.Postman;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LogManager.getLogger(WeatherServiceImpl.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherArchiveRepository weatherArchiveRepository;

    @Override
    public Weather create(String key, JsonNode node) {
        Weather weather  = new Weather();
        weather.setKey(key);
        // firing initial validations - handling exceptions
        if(node.get("time") != null) { weather.setTime(node.get("time").longValue()); }
        if(node.get("summary") != null) { weather.setSummary(node.get("summary").textValue()); }
        if(node.get("icon") != null) { weather.setIcon(node.get("icon").textValue()); }
        if(node.get("nearestStormDistance") != null) { weather.setNearestStormDistance(node.get("nearestStormDistance").floatValue()); }
        if(node.get("precipIntensity") != null) { weather.setPrecipIntensity(node.get("precipIntensity").doubleValue()); }
        if(node.get("precipIntensityError") != null) { weather.setPrecipIntensityError(node.get("precipIntensityError").doubleValue()); }
        if(node.get("precipProbability") != null) { weather.setPrecipProbability(node.get("precipProbability").doubleValue()); }
        if(node.get("precipType") != null) { weather.setPrecipType( node.get("precipType").textValue()); }
        if(node.get("temperature") != null) { weather.setTemperature(node.get("temperature").doubleValue()); }
        if(node.get("apparentTemperature") != null) { weather.setApparentTemperature(node.get("apparentTemperature").doubleValue()); }
        if(node.get("dewPoint") != null) { weather.setDewPoint(node.get("dewPoint").doubleValue()); }
        if(node.get("humidity") != null) { weather.setHumidity(node.get("humidity").doubleValue()); }
        if(node.get("pressure") != null) { weather.setPressure(node.get("pressure").doubleValue()); }
        if(node.get("windSpeed") != null) { weather.setWindSpeed(node.get("windSpeed").doubleValue()); }
        if(node.get("windGust") != null) { weather.setWindGust(node.get("windGust").doubleValue()); }
        if(node.get("windBearing") != null) { weather.setWindBearing(node.get("windBearing").doubleValue()); }
        if(node.get("cloudCover") != null) { weather.setCloudCover(node.get("cloudCover").doubleValue()); }
        if(node.get("uvIndex") != null) { weather.setUvIndex(node.get("uvIndex").intValue()); }
        if(node.get("visibility") != null) { weather.setVisibility(node.get("visibility").doubleValue()); }
        if(node.get("ozone") != null) { weather.setOzone(node.get("ozone").doubleValue()); }

        logger.info("Saving a weather update: ", key);
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    @Override
    public Weather findByKey(String key) {
        return weatherRepository.findByKey(key);
    }

    @Override
    public void checkAndUpdate() {
        logger.info("Checking for weather updates");
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(new Date());

        Weather aElement = findByKey(Constants.COORDINATES.entrySet().stream().findFirst().get().getKey());

        if (aElement == null) {
            getUpdates();
            return;
        }
        logger.info("Received a element: ", aElement.getKey() + " - " + aElement.getTime());
        Calendar lastUpdatedCalender =  Calendar.getInstance();
        lastUpdatedCalender.setTimeInMillis(TimeUnit.SECONDS.toMillis(aElement.getTime()));
        if(calendarNow.get(Calendar.DAY_OF_WEEK) != lastUpdatedCalender.get(Calendar.DAY_OF_WEEK)) {
            archive();
            deleteAll();
            getUpdates();
        }
    }

    public String deleteAll() {
        weatherRepository.deleteAll();
        return "Deleted all the data";
    }

    public void archive() {
        List<Weather> weatherList = getAll();
        weatherArchiveRepository.save(new WeatherArchive(
                weatherList.get(0).getTime(), weatherList
        ));
    }

    @Override
    public void getUpdates() {
        logger.info("Getting Updates");
        Constants.COORDINATES.forEach((key, value) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (findByKey(key) == null) {
                    JsonNode root = mapper.readTree(Postman.get(ConnectionParams.weatherUrl + value).getBody());
                    create(key, root.get("currently"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
