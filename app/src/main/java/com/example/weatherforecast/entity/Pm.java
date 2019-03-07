package com.example.weatherforecast.entity;

/**
 * Created by admin on 2018/11/1.
 */

public class Pm {

    //返回的pm json信息：
    /*
    {
    "resultcode":"200",
    "reason":"SUCCESSED!",
    "result":[
        {
            "city":"北京",
            "PM2.5":"93",
            "AQI":"130",
            "quality":"轻度污染",
            "PM10":"126",
            "CO":"1.1",
            "NO2":"96",
            "O3":"14",
            "SO2":"7",
            "time":"2018-11-01 21:44:50"
        }
    ],
    "error_code":0}
    */

    public String PM;
    public String quality;

    @Override
    public String toString() {
        return "Pm{" +
                "PM='" + PM + '\'' +
                ", quality='" + quality + '\'' +
                '}';
    }
}
