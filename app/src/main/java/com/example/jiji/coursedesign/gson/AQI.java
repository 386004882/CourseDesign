package com.example.jiji.coursedesign.gson;

/**
 * Created by jiji on 2017/5/4.
 */

public class AQI {
    public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
