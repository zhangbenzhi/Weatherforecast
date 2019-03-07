package com.example.weatherforecast.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
			holder.tv_suggestion = (TextView) v
					.findViewById(R.id.tv_suggestion);
			holder.tv_quality = (TextView) v.findViewById(R.id.tv_quality);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		try {
			Weather weather = (Weather) getItem(position);
			ImageUtil.setImg(holder.iv, weather.getState());
			holder.tv_date.setText(weather.getDate());
			holder.tv_state.setText(weather.getState());
			holder.tv_suggestion.setText(weather.getDescription());
			holder.tv_temp.setText(weather.getTemperature());
			holder.tv_wind.setText(weather.getWind());
			holder.tv_quality.setText(weather.getQuality());
		} catch (Exception e) {
			Log.e("TAG", e.getMessage());
		}

		return v;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv_date, tv_state, tv_wind, tv_temp, tv_quality,
				tv_suggestion;
	}

}
