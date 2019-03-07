package com.example.weatherforecast.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.weatherforecast.constant.Constant;
import com.example.weatherforecast.entity.Pm;
import com.example.weatherforecast.entity.Weather;

/**
 * 网络请求天气数据业务类：
 */
public class RequestUtil {

    /**
     * 获取天气信息：
     *
     * @param city
     * @return
     */
    public static String getWeater(String city) {
        URL url = null;
        try {
            url = new URL(Constant.weatherUrl + "?cityname=" + city + "&key=" + Constant.weatherApikey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return get(url);
    }

    /**
     * 获取pm信息
     *
     * @param city
     * @return
     */
    public static String getPM(String city) {
        URL url = null;
        try {
            url = new URL(Constant.pmUrl + "?city=" + city + "&key=" + Constant.pmApikey);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return get(url);
    }

    /**
     * 从网络获取天气数据
     *
     * @return Json字符串
     */
    public static String get(URL url) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sb = new StringBuffer();
        try {
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();// 获取HttpUrlConnection对象；
            connection.setRequestMethod("GET");// Get请求
            connection.connect();// 建立连接；
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            // 读取数据并拼接：
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
                sb.append("\r\n");
            }
            reader.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;// 返回Json字符串；
    }

    // Json解析天气信息：
    public static List<Weather> parseWeather(String json) {
        List<Weather> weathers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Object code = jsonObject.get("resultcode");
            //证明数据返回成功
            if ("200".equals(code)) {
                Object result = jsonObject.get("result");
                if (result instanceof JSONObject) {
                    JSONObject jsonResult = (JSONObject) result;
                    Object future = jsonResult.get("future");
                    if (future instanceof JSONObject) {
                        JSONObject jsonFuture = (JSONObject) future;
                        for (int i = 0; i < 7; i++) {
                            String dateAfter = getDateAfter(i);
                            Weather weatherBean = new Weather();
                            Object weather = jsonFuture.get("day_" + dateAfter);
                            if (weather instanceof JSONObject) {
                                JSONObject jsonWeather = (JSONObject) weather;
                                weatherBean.setState(jsonWeather.get("weather") + "");
                                weatherBean.setTemperature("温度：" + jsonWeather.get("temperature"));
                                weatherBean.setDescription("天气:" + jsonWeather.get("weather"));
                                weatherBean.setWind("风力：" + jsonWeather.get("wind"));
                                weatherBean.setDate("日期：" + jsonWeather.get("date"));
                            }
                            weathers.add(weatherBean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TAG",e.getMessage());
        }
        return weathers;
    }

    // Json解析pm信息：
    public static Pm parsePm(String json) {
        Pm pmBean = new Pm();
        try {
            JSONObject jsonObject = new JSONObject(json);
            Object code = jsonObject.get("resultcode");
            //证明数据返回成功
            if ("200".equals(code)) {
                Object result = jsonObject.get("result");
                if (result instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) result;
                    if (jsonArray.length() > 0) {
                        Object jsonCity = jsonArray.get(0);
                        if (jsonCity instanceof JSONObject) {
                            JSONObject jsonPm = (JSONObject) jsonCity;
                            Object pm = jsonPm.get("PM2.5");
                            Object quality = jsonPm.get("quality");
                            pmBean.PM = "PM2.5: " + pm;
                            pmBean.quality = quality + "";
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return pmBean;
    }

    /**
     * 得到几天后的时间
     *
     * @param day
     * @return
     */
    public static String getDateAfter(int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        Date time = now.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(time);
        return date;
    }
}
