package com.anuradha.weather.model;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Document(collection = "forecast_of_the_day")
public class Weather {

    @Id
    String id;
    String key;
    long time;
    String summary;
    String icon;
    float nearestStormDistance;
    double precipIntensity;
    @Nullable
    double precipIntensityError;
    double precipProbability;
    String precipType;
    double temperature;
    double apparentTemperature;
    double dewPoint;
    double humidity;
    double pressure;
    double windSpeed;
    double windGust;
    double windBearing;
    double cloudCover;
    int uvIndex;
    double visibility;
    double ozone;

//    public Weather(String key, long time, String summary, String icon, float nearestStormDistance, double precipIntensity, double precipIntensityError, double precipProbability, String precipType, double temperature, double apparentTemperature, double dewPoint, double humidity, double pressure, double windSpeed, double windGust, double windBearing, double cloudCover, int uvIndex, double visibility, double ozone) {
//        this.key = key;
//        this.time = time;
//        this.summary = summary;
//        this.icon = icon;
//        this.nearestStormDistance = nearestStormDistance;
//        this.precipIntensity = precipIntensity;
//        this.precipIntensityError = precipIntensityError;
//        this.precipProbability = precipProbability;
//        this.precipType = precipType;
//        this.temperature = temperature;
//        this.apparentTemperature = apparentTemperature;
//        this.dewPoint = dewPoint;
//        this.humidity = humidity;
//        this.pressure = pressure;
//        this.windSpeed = windSpeed;
//        this.windGust = windGust;
//        this.windBearing = windBearing;
//        this.cloudCover = cloudCover;
//        this.uvIndex = uvIndex;
//        this.visibility = visibility;
//        this.ozone = ozone;
//    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getNearestStormDistance() {
        return nearestStormDistance;
    }

    public void setNearestStormDistance(float nearestStormDistance) {
        this.nearestStormDistance = nearestStormDistance;
    }

    public double getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(double precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public double getPrecipIntensityError() {
        return precipIntensityError;
    }

    public void setPrecipIntensityError(double precipIntensityError) {
        this.precipIntensityError = precipIntensityError;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindGust() {
        return windGust;
    }

    public void setWindGust(double windGust) {
        this.windGust = windGust;
    }

    public double getWindBearing() {
        return windBearing;
    }

    public void setWindBearing(double windBearing) {
        this.windBearing = windBearing;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getOzone() {
        return ozone;
    }

    public void setOzone(double ozone) {
        this.ozone = ozone;
    }

    public String getTimeString() {
        Date date = new Date(TimeUnit.SECONDS.toMillis(getTime()));
        SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        return format.format(date);
    }
}
