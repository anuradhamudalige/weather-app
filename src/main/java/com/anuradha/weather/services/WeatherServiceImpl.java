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
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private MongoTemplate mongoTemplate;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherArchiveRepository weatherArchiveRepository;

    /**
     * @param key same key defines inside the {@link Constants.COORDINATES}
     * @param node take parameter type JsonNode returned from darkSky URL
     *
     * @return Weather type object which persists on DB
     * **/
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

    /**
     * Return all updates for the specific day
     * @return List<Weather>
     * **/
    @Override
    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    /**
     * @param key takes String type key which defines inside the {@link Constants.COORDINATES}
     * @return Weather
     * **/
    @Override
    public Weather findByKey(String key) {
        return weatherRepository.findByKey(key);
    }

    /**
     * Checks if weather updates are persists on the collection if not
     * {@link #getUpdates()} will be called
     *
     * If stored updates are present then checks if the current updates are for the current day
     * if not detects its a new day, executes {@link #archive()} to send current stored updates to the
     * buffered collection the {@link #deleteAll()} to delete the current updates.
     * Finally use {@link #getUpdates()} to retrieve new updates.
     * **/
    @Override
    public void checkAndUpdate() {
        logger.info("Checking for stored weather updates");
        Calendar calendarNow = Calendar.getInstance();
        calendarNow.setTime(new Date());

        Weather storedUpdate = findByKey(Constants.COORDINATES.entrySet().stream().findFirst().get().getKey());

        if (storedUpdate == null) {
            getUpdates();
            return;
        }
        logger.info("Received a stored weather update: ", storedUpdate.getKey() + " - " + storedUpdate.getTime());
        Calendar lastUpdatedCalender =  Calendar.getInstance();
        lastUpdatedCalender.setTimeInMillis(TimeUnit.SECONDS.toMillis(storedUpdate.getTime()));
        if(calendarNow.get(Calendar.DAY_OF_WEEK) != lastUpdatedCalender.get(Calendar.DAY_OF_WEEK)) {
            archive();
            deleteAll();
            getUpdates();
        }
    }

    /**
     * This method will execute by the scheduler twice a day.
     * This will archive the buffered updates to new collection if they are more than 3 days old
     * **/
    @Override
    public void houseKeepData() {
         List<WeatherArchive> weatherArchives = weatherArchiveRepository.findAll();
         if (weatherArchives == null || weatherArchives.size() == 0) {
             return;
         }
         long timeDif = new Date().getTime() - TimeUnit.SECONDS.toMillis(weatherArchives.get(0).getLastUpdateTime());
         if ( timeDif >= Constants.TIMETOEXPIRE) {
             weatherArchives.forEach(weatherArchive -> {
                 mongoTemplate.insert(weatherArchive, "weather_archive");
             });
             logger.info("Archiving weather buffer");
         }
        logger.info("Time difference: " + timeDif + " - " + Constants.TIMETOEXPIRE);
    }

    /**
     * Delete all the stored updates for the current day
     * **/
    public String deleteAll() {
        weatherRepository.deleteAll();
        return "Deleted all the data";
    }

    /**
     * Archive all the stored updates of the current day to a buffered collection
     * **/
    public void archive() {
        List<Weather> weatherList = getAll();
        weatherArchiveRepository.save(new WeatherArchive(
                weatherList.get(0).getTime(), weatherList
        ));
    }

    /**
     * Method will retrieve new updates if stored updates are not present at the collection
     * **/
    @Override
    public void getUpdates() {
        logger.info("Getting Updates");
        Constants.COORDINATES.forEach((key, value) -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (findByKey(key) == null) {
                    JsonNode root = mapper.readTree(Postman.get(ConnectionParams.weatherUrl + value).getBody());
                    create(key, root.get("currently"));
                    logger.info(root.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
