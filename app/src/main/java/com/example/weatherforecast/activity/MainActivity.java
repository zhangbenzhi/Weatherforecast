package com.example.weatherforecast.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.weatherforecast.R;
import com.example.weatherforecast.adapter.WeatherAdapter;
import com.example.weatherforecast.entity.Pm;
import com.example.weatherforecast.entity.Weather;
import com.example.weatherforecast.util.ImageUtil;
import com.example.weatherforecast.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ListView lv_weather;// 一周天气状况列表；
    private View headerView;// 列表的头部；
    private WeatherAdapter adapter;// 天气适配器；
    private String city = "北京";// 开始默认为北京；
    private Button bt_search;
    private EditText et_city;
    private ImageView iv_weather;
    private TextView tv_date, tv_pm, tv_quality;
    private ProgressBar progressBar;
    String[] spinnerKeys = {"清新主题", "夜间主题"};
    Spinner spinner;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();// 初始化视图；
        setListener();// 设置监听；
        setSpinner();
        location();
    }

    private void location() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //配置
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
    }

    private void setSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerKeys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                changeTheame(pos == 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 更改主题
     *
     * @param isDay
     */
    private void changeTheame(boolean isDay) {
        if (isDay) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setListener() {
        bt_search.setOnClickListener(this);
    }

    @SuppressLint("InflateParams")
    private void initView() {
        lv_weather = (ListView) findViewById(R.id.lv_weather);
        headerView = getLayoutInflater().inflate(R.layout.lv_header, null);
        iv_weather = (ImageView) headerView.findViewById(R.id.iv_weather);
        tv_date = (TextView) headerView.findViewById(R.id.tv_date);
        tv_pm = (TextView) headerView.findViewById(R.id.tv_pm);
        tv_quality = (TextView) headerView.findViewById(R.id.tv_quality_header);
        bt_search = (Button) headerView.findViewById(R.id.bt_search);
        et_city = (EditText) headerView.findViewById(R.id.et_city);
        lv_weather.addHeaderView(headerView);// 为列表添加头布局；
        adapter = new WeatherAdapter(getLayoutInflater());
        lv_weather.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        List<Weather> list = new ArrayList<>();
        adapter.add(list);
        et_city.setHint("请输入城市");
    }

    private void request() {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String pmJson = RequestUtil.getPM(city);
                final Pm pm = RequestUtil.parsePm(pmJson);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pm != null) {
                            tv_pm.setText(pm.PM);
                            tv_quality.setText(pm.quality);
                        }
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = RequestUtil.getWeater(city);
                final List<Weather> list = RequestUtil.parseWeather(json); //
                // 运行在主线程：
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if (list == null) {
                            Toast.makeText(MainActivity.this, "获取网络数据异常！", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        adapter.add(list);
                        if (list.size() > 0) {
                            ImageUtil.setImg(iv_weather, list.get(0).getState());
                            tv_date.setText(list.get(0).getDate());
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                if (TextUtils.isEmpty(et_city.getText())) {
                    Toast.makeText(this, "请输入城市！", Toast.LENGTH_SHORT).show();
                    return;
                }
                city = et_city.getText().toString();
                request();
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String city = location.getCity();
            et_city.setText(city);
            request();
        }
    }
}
