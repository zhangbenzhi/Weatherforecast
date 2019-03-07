package com.example.weatherforecast.entity;

/**
 * 天气实体类：
 */
public class Weather {

    //返回的天气json信息
    /*
    "resultcode":"200",
	"reason":"查询成功",
	"result":{
		"sk":{
			"temp":"11",
			"wind_direction":"西北风",
			"wind_strength":"3级",
			"humidity":"34%",
			"time":"21:18"
		},
		"today":{
			"temperature":"5℃~17℃",
			"day_back":"多云转晴",
			"weather_id":{
				"fa":"01",
				"fb":"00"
			},
			"wind":"西北风微风",
			"week":"星期六",
			"city":"北京",
			"date_y":"2018年10月27日",
			"dressing_index":"较舒适",
			"dressing_advice":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
			"uv_index":"弱",
			"comfort_index":"",
			"wash_index":"较适宜",
			"travel_index":"较不宜",
			"exercise_index":"较不宜",
			"drying_index":""
		},
		"future":{
			"day_20181027":{
				"temperature":"5℃~17℃",
				"day_back":"多云转晴",
				"weather_id":{
					"fa":"01",
					"fb":"00"
				},
				"wind":"西北风微风",
				"week":"星期六",
				"date":"20181027"
			},
			"day_20181028":{
				"temperature":"4℃~17℃",
				"day_back":"多云转晴",
				"weather_id":{
					"fa":"01",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期日",
				"date":"20181028"
			},
			"day_20181029":{
				"temperature":"3℃~15℃",
				"day_back":"晴",
				"weather_id":{
					"fa":"00",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期一",
				"date":"20181029"
			},
			"day_20181030":{
				"temperature":"1℃~14℃",
				"day_back":"晴",
				"weather_id":{
					"fa":"00",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期二",
				"date":"20181030"
			},
			"day_20181031":{
				"temperature":"4℃~16℃",
				"day_back":"晴",
				"weather_id":{
					"fa":"00",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期三",
				"date":"20181031"
			},
			"day_20181101":{
				"temperature":"4℃~17℃",
				"day_back":"多云转晴",
				"weather_id":{
					"fa":"01",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期四",
				"date":"20181101"
			},
			"day_20181102":{
				"temperature":"1℃~14℃",
				"day_back":"晴",
				"weather_id":{
					"fa":"00",
					"fb":"00"
				},
				"wind":"北风微风",
				"week":"星期五",
				"date":"20181102"
			}
		}
	},
	"error_code":0
}
*/


    private String state;// 天气描述：如晴天，阴天等。。。
    private String date;// 日期；年月日；
    private String wind;// 风力；
    private String temperature;// 温度；
    private String quality;// 空气质量；
    private String description;// 出行指示；如：适宜出行，不适宜出行等。。。。

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Weather() {
    }


}
