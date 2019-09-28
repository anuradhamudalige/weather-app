package com.anuradha.weather.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    // holds location name as the keys and its GPS coordinates
    public static final Map<String, String> COORDINATES = new HashMap<String, String>() {{
        put("Campbell, CA", "37.2872,121.9500");
        put("Omaha, NE", "41.2565,95.9345");
        put("Austin, TX", "30.2672,97.7431");
        put("Niseko, Japan", "42.8048,140.6874");
        put("Nara, Japan", "34.6851,135.8048");
        put("Jakarta, Indonesia", "6.2088,106.8456");
    }};

    // set time in milliseconds to run the housekeep process default is 259200000 which is 3 days
    public static final long TIMETOEXPIRE = 259200000;
}
