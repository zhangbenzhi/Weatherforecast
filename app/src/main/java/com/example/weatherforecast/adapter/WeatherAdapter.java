package com.example.weatherforecast.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherforecast.R;
import com.example.weatherforecast.entity.Weather;
import com.example.weatherforecast.util.ImageUtil;

public class WeatherAdapter extends MyBaseAdapter<Weather> {

	public WeatherAdapter(LayoutInflater inflater) {
		super(inflater);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {

		ViewHolder holder = null;
		if (v == null) {
			v = mInflater.inflate(R.layout.lv_item, null);
			holder = new ViewHolder();
			holder.iv = (ImageView) v.findViewById(R.id.iv_weather);
			holder.tv_date = (TextView) v.findViewById(R.id.tv_date);
			holder.tv_state = (TextView) v.findViewById(R.id.tv_state);
			holder.tv_wind = (TextView) v.findViewById(R.id.tv_wind);
			holder.tv_temp = (TextView) v.findViewById(R.id.tv_temp);
			holder.cloth= (TextView) v.findViewById(R.id.cloth);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		try {
			Weather weather = (Weather) getItem(position);
			ImageUtil.setImg(holder.iv, weather.getState());
			holder.tv_date.setText(weather.getDate());
			holder.tv_state.setText(weather.getState());
			holder.tv_temp.setText(weather.getTemperature());
			holder.tv_wind.setText(weather.getWind());
			try{
				String[] split = weather.getTemperature().split("℃~");
				String[] little = split[0].split("温度：");
				if(Integer.parseInt(little[1])<0){
					holder.cloth.setText("穿衣指数：天气太冷，请穿厚点，穿羽绒服吧");
				}else if(Integer.parseInt(little[1])<5){
					holder.cloth.setText("穿衣指数：天气较冷，请穿厚点，穿毛衣吧");
				}else if(Integer.parseInt(little[1])<15){
					holder.cloth.setText("穿衣指数：天气较热，请穿少点，穿长袖吧");
				}else if(Integer.parseInt(little[1])<25){
					holder.cloth.setText("穿衣指数：天气太热，请穿少点，穿短袖吧");
				}
			}catch (Exception e){
				Toast.makeText(holder.tv_date.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(holder.tv_date.getContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
		}

		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv_date, tv_state, tv_wind, tv_temp, cloth;
	}

}
