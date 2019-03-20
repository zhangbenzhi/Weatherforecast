package com.example.weatherforecast.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private ListView lv_weather;// 一周天气状况列表；
    private WeatherAdapter adapter;// 天气适配器；
    private String city = "北京";// 开始默认为北京；
    private Button bt_search;
    private EditText et_city;
    private ImageView iv_weather, iv_wear;
    private TextView tv_date, tv_pm, tv_quality;
    private RadioGroup radioGroup;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refresh;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private Map<Integer,int[]> clothes=new HashMap<>();
    int key=0;
    int value=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClothes();
        initView();// 初始化视图；
        setListener();// 设置监听；
        location();
    }

    private void initClothes() {
        int[] woman01=new int[4];
        woman01[0]=R.drawable.lianyiqun;
        woman01[1]=R.drawable.changxiu;
        woman01[2]=R.drawable.maoyi;
        woman01[3]=R.drawable.yurongfu;
        clothes.put(0,woman01);
        int[] woman02=new int[4];
        woman02[0]=R.drawable.duanxiu01;
        woman02[1]=R.drawable.chenshan01;
        woman02[2]=R.drawable.xizhuang01;
        woman02[3]=R.drawable.dayi01;
        clothes.put(1,woman02);
        int[] man01=new int[4];
        man01[0]=R.drawable.lianyiqun02;
        man01[1]=R.drawable.changxiu02;
        man01[2]=R.drawable.maoyi02;
        man01[3]=R.drawable.yurongfu02;
        clothes.put(2,man01);
        int[] man02=new int[4];
        man02[0]=R.drawable.duanxiu02;
        man02[1]=R.drawable.chenshan02;
        man02[2]=R.drawable.xizhuang02;
        man02[3]=R.drawable.dayi02;
        clothes.put(3,man02);
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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb01) {
                    Intent intent=new Intent(MainActivity.this,ChoiceActivity.class);
                    intent.putExtra("type",0);
                    startActivityForResult(intent,100);
                } else if (checkedId == R.id.rb02) {
                    Intent intent=new Intent(MainActivity.this,ChoiceActivity.class);
                    intent.putExtra("type",1);
                    startActivityForResult(intent,100);
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
                                    iv_wear.setImageResource(clothes.get(key)[3]);
                                } else if (Integer.parseInt(little[1]) < 5) {
                                    iv_wear.setImageResource(clothes.get(key)[2]);
                                } else if (Integer.parseInt(little[1]) < 15) {
                                    iv_wear.setImageResource(clothes.get(key)[1]);
                                } else if (Integer.parseInt(little[1]) < 25) {
                                    iv_wear.setImageResource(clothes.get(key)[0]);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            key = data.getIntExtra("key", 0);
            value = data.getIntExtra("value", 0);
            iv_wear.setImageResource(clothes.get(key)[value]);
        }catch (Exception e){}

    }
}
