package com.anuradha.weather.repository;

import com.anuradha.weather.model.WeatherArchive;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherArchiveRepository extends MongoRepository<WeatherArchive, String> {
}
