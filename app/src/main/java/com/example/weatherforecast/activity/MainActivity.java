package com.example.weatherforecast.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
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
    private WeatherAdapter adapter;// 天气适配器；
    private String city = "北京";// 开始默认为北京；
    private Button bt_search;
    private EditText et_city;
    private ImageView iv_weather, iv_wear, cloth01, cloth02, cloth03, cloth04;
    private TextView tv_date, tv_pm, tv_quality;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refresh;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private int checked_style = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();// 初始化视图；
        setListener();// 设置监听；
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

    private void setListener() {
        bt_search.setOnClickListener(this);
    }

    @SuppressLint("InflateParams")
    private void initView() {
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        lv_weather = (ListView) findViewById(R.id.lv_weather);
        iv_weather = (ImageView) findViewById(R.id.iv_weather);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_pm = (TextView) findViewById(R.id.tv_pm);
        tv_quality = (TextView) findViewById(R.id.tv_quality_header);
        bt_search = (Button) findViewById(R.id.bt_search);
        et_city = (EditText) findViewById(R.id.et_city);
        iv_wear = (ImageView) findViewById(R.id.iv_wear);
        cloth01 = (ImageView) findViewById(R.id.cloth01);
        cloth02 = (ImageView) findViewById(R.id.cloth02);
        cloth03 = (ImageView) findViewById(R.id.cloth03);
        cloth04 = (ImageView) findViewById(R.id.cloth04);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        adapter = new WeatherAdapter(getLayoutInflater());
        lv_weather.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        List<Weather> list = new ArrayList<>();
        adapter.add(list);
        et_city.setHint("请输入城市");

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
        cloth01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wear.setImageResource(checked_style == 0 ? R.drawable.lianyiqun : R.drawable.lianyiqun02);
            }
        });
        cloth02.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wear.setImageResource(checked_style == 0 ? R.drawable.changxiu : R.drawable.changxiu02);
            }
        });
        cloth03.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wear.setImageResource(checked_style == 0 ? R.drawable.maoyi : R.drawable.maoyi02);
            }
        });
        cloth04.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_wear.setImageResource(checked_style == 0 ? R.drawable.yurongfu : R.drawable.yurongfu02);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb01) {
                    checked_style = 0;
                    cloth01.setImageResource(R.drawable.lianyiqun);
                    cloth02.setImageResource(R.drawable.changxiu);
                    cloth03.setImageResource(R.drawable.maoyi);
                    cloth04.setImageResource(R.drawable.yurongfu);
                } else if (checkedId == R.id.rb02) {
                    checked_style = 1;
                    cloth01.setImageResource(R.drawable.lianyiqun02);
                    cloth02.setImageResource(R.drawable.changxiu02);
                    cloth03.setImageResource(R.drawable.maoyi02);
                    cloth04.setImageResource(R.drawable.yurongfu02);
                }
            }
        });
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
                        finishRequest();
                        if (list == null) {
                            Toast.makeText(MainActivity.this, "获取网络数据异常！", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        adapter.add(list);
                        if (list.size() > 0) {
                            ImageUtil.setImg(iv_weather, list.get(0).getState());
                            tv_date.setText(list.get(0).getDate());
                            try {
                                String[] split = list.get(0).getTemperature().split("℃~");
                                String[] little = split[0].split("温度：");
                                if (Integer.parseInt(little[1]) < 0) {
                                    iv_wear.setImageResource(checked_style==0?R.drawable.yurongfu:R.drawable.yurongfu02);
                                } else if (Integer.parseInt(little[1]) < 5) {
                                    iv_wear.setImageResource(checked_style==0?R.drawable.maoyi:R.drawable.maoyi02);
                                } else if (Integer.parseInt(little[1]) < 15) {
                                    iv_wear.setImageResource(checked_style==0?R.drawable.changxiu:R.drawable.changxiu02);
                                } else if (Integer.parseInt(little[1]) < 25) {
                                    iv_wear.setImageResource(checked_style==0?R.drawable.lianyiqun:R.drawable.lianyiqun02);
                                }
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }).start();
    }

    private void finishRequest() {
        refresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                if (TextUtils.isEmpty(et_city.getText().toString())) {
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
            if (!TextUtils.isEmpty(city)) {
                et_city.setText(city);
                request();
            } else {
                Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
