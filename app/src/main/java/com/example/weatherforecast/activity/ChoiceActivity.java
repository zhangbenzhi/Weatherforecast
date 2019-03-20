package com.example.weatherforecast.activity;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.weatherforecast.R;

import java.util.HashMap;
import java.util.Map;

public class ChoiceActivity extends AppCompatActivity {

    ImageView cloth01, cloth02, cloth03, cloth04;
    TabLayout tabLayout;
    private Map<Integer,int[]> clothes=new HashMap<>();
    int keyPos=0;
    int valuePos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        cloth01 = (ImageView) findViewById(R.id.cloth01);
        cloth02 = (ImageView) findViewById(R.id.cloth02);
        cloth03 = (ImageView) findViewById(R.id.cloth03);
        cloth04 = (ImageView) findViewById(R.id.cloth04);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);

        initClothes();

        final int type = getIntent().getIntExtra("type", 0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(type==0){
                    if(tab.getPosition()==0){
                            keyPos=0;
                            cloth01.setImageResource(clothes.get(0)[0]);
                            cloth02.setImageResource(clothes.get(0)[1]);
                            cloth03.setImageResource(clothes.get(0)[2]);
                            cloth04.setImageResource(clothes.get(0)[3]);
                        }else{
                            keyPos=1;
                            cloth01.setImageResource(clothes.get(1)[0]);
                            cloth02.setImageResource(clothes.get(1)[1]);
                            cloth03.setImageResource(clothes.get(1)[2]);
                            cloth04.setImageResource(clothes.get(1)[3]);
                        }
                    }else{
                    if(tab.getPosition()==0){
                        keyPos=2;
                        cloth01.setImageResource(clothes.get(2)[0]);
                        cloth02.setImageResource(clothes.get(2)[1]);
                        cloth03.setImageResource(clothes.get(2)[2]);
                        cloth04.setImageResource(clothes.get(2)[3]);
                    }else{
                        keyPos=3;
                        cloth01.setImageResource(clothes.get(3)[0]);
                        cloth02.setImageResource(clothes.get(3)[1]);
                        cloth03.setImageResource(clothes.get(3)[2]);
                        cloth04.setImageResource(clothes.get(3)[3]);
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        switch (type){
            case 0:
                tabLayout.addTab(tabLayout.newTab().setText("美丽少女"),false);
                tabLayout.addTab(tabLayout.newTab().setText("职业女性"),true);
                break;
            case 1:
                tabLayout.addTab(tabLayout.newTab().setText("阳光男孩"),false);
                tabLayout.addTab(tabLayout.newTab().setText("成功男士"),true);
                break;
        }
        final Intent intent=new Intent();
        cloth01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuePos=0;
                intent.putExtra("key",keyPos);
                intent.putExtra("value",valuePos);
                setResult(200,intent);
                finish();
            }
        });
        cloth02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuePos=1;
                intent.putExtra("key",keyPos);
                intent.putExtra("value",valuePos);
                setResult(200,intent);
                finish();
            }
        });
        cloth03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuePos=2;
                intent.putExtra("key",keyPos);
                intent.putExtra("value",valuePos);
                setResult(200,intent);
                finish();
            }
        });
        cloth04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuePos=3;
                intent.putExtra("key",keyPos);
                intent.putExtra("value",valuePos);
                setResult(200,intent);
                finish();
            }
        });
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
}
