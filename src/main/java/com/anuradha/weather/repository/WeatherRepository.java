package com.anuradha.weather.repository;

import com.anuradha.weather.model.Weather;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<Weather, String> {
    Weather findByKey(String key);
}
