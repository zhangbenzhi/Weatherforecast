package com.example.weatherforecast.util;

import java.util.HashMap;
import java.util.Map;

import com.example.weatherforecast.R;

import android.widget.ImageView;

/**
 * 天气图片业务类：
 */
public class ImageUtil {

    /**
     * 根据天气状况给图像展示相应的图片；
     *
     * @param iv  图像控件
     * @param key 天气状况
     */
    public static void setImg(ImageView iv, String key) {
        Map<String, Integer> imgs = new HashMap<>();
        imgs.put("暴雪", R.drawable.snowstorm);// 暴雪
        imgs.put("暴雨", R.drawable.rainstorm);// 暴雨
        imgs.put("冰雹", R.drawable.bingbao);// 冰雹
        imgs.put("大雪", R.drawable.heavy_snow);// 大雪
        imgs.put("大雨", R.drawable.heavy_rain);// 大雨
        imgs.put("多云", R.drawable.cloudy);// 多云
        imgs.put("雷阵雨", R.drawable.leizhenyu);// 雷阵雨
        imgs.put("晴天", R.drawable.sun);// 晴天
        imgs.put("雾", R.drawable.fog);// 雾
        imgs.put("雾霾", R.drawable.haze);// 雾霾
        imgs.put("小雪", R.drawable.spit_snow);// 小雪
        imgs.put("小雨", R.drawable.spit);// 小雨
        imgs.put("阴", R.drawable.duoyun);// 多云
        imgs.put("多云", R.drawable.duoyun);// 多云
        imgs.put("雨夹雪", R.drawable.rain_with_snow);// 雨夹雪
        imgs.put("阵雨", R.drawable.zhenyu);// 阵雨
        imgs.put("中雪", R.drawable.moderate_snow);// 中雪
        imgs.put("中雨", R.drawable.moderate_rain);// 中雨
        imgs.put("雪", R.drawable.moderate_snow);// 雪

        // 判断天气状况值中是否存在某个关键字：根据关键字来给ImageView填充图片
        if (imgs.containsKey(key)) {
            iv.setImageResource(imgs.get(key));
        } else if (key.contains("云") || key.contains("阴")) {
            iv.setImageResource(imgs.get("多云"));
        } else if (key.contains("雾霾")) {
            iv.setImageResource(imgs.get("雾霾"));
        } else if (key.contains("大雨")) {
            iv.setImageResource(imgs.get("大雨"));
        } else if (key.contains("中雨")) {
            iv.setImageResource(imgs.get("中雨"));
        } else if (key.contains("暴雨")) {
            iv.setImageResource(imgs.get("暴雨"));
        } else if (key.contains("小雨")) {
            iv.setImageResource(imgs.get("小雨"));
        } else if (key.contains("雪")) {
            iv.setImageResource(imgs.get("雪"));
        } else if (key.contains("雾")) {
            iv.setImageResource(imgs.get("雾"));
        } else {
            iv.setImageResource(imgs.get("晴天"));
        }
    }

}
