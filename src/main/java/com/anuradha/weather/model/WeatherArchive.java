package com.anuradha.weather.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "weather_archive_buffer")
public class WeatherArchive {
    @Id
    String id;
    long lastUpdateTime;
    List<Weather> updates;

    public WeatherArchive(long lastUpdateTime, List<Weather> updates) {
        this.lastUpdateTime = lastUpdateTime;
        this.updates = updates;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<Weather> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Weather> updates) {
        this.updates = updates;
    }
}