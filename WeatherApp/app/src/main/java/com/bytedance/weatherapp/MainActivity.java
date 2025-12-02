package com.bytedance.weatherapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.weatherapp.activity.DetailActivity;
import com.bytedance.weatherapp.adapter.WeatherAdapter;
import com.bytedance.weatherapp.bean.WeatherBean;
import com.bytedance.weatherapp.utils.WeatherApi;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private LinearLayout cities[]=new LinearLayout[4];
    private int current;
    private int ids[]={R.id.beijing,R.id.shanghai,R.id.guangzhou,R.id.shenzhen};
    private String cityIds[]={"110101","310100","440100","440300"};

    private TextView mainCity;
    private TextView mainWeather;
    private TextView maxMin;
    private TextView mainDayWeather;
    private TextView mainDayTemp;
    private TextView mainDayWind;
    private TextView mainNightWeather;
    private TextView mainNightTemp;
    private TextView mainNightWind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        current=getIntent().getIntExtra("cityIndex",0);
        initViews();
    }


    private void initViews()
    {

        mainCity=findViewById(R.id.main_city);
        mainWeather=findViewById(R.id.main_weather);
        maxMin=findViewById(R.id.max_min);
        mainDayWeather=findViewById(R.id.main_day_weather);
        mainDayTemp=findViewById(R.id.main_day_temp);
        mainDayWind=findViewById(R.id.main_day_wind);
        mainNightWeather=findViewById(R.id.main_night_weather);
        mainNightTemp=findViewById(R.id.main_night_temp);
        mainNightWind=findViewById(R.id.main_night_wind);
        renderWeatherData();
        findViewById(R.id.main_forsee).setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
            intent.putExtra("cityId",cityIds[current]);
            intent.putExtra("cityIndex",current);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        for(int i=0;i<4;i++)
        {
            final int now=i;
            cities[i]=findViewById(ids[i]);
            cities[i].setOnClickListener(v->{
                current=now;
                renderWeatherData();
                toggleButtons();
            });
        }
        toggleButtons();
    }

    private void renderWeatherData()
    {
        final String cityId=cityIds[current];
        WeatherApi.getWeathers(cityId,new WeatherApi.ApiCallback<List<WeatherBean>>() {
            @Override
            public void onSuccess(List<WeatherBean> data) {
                WeatherBean todayWeather=data.get(0);
                String city=cityId.equals("110101")?"北京":cityId.equals("310100")?"上海":cityId.equals("440100")?"广州":"深圳";
                mainCity.setText(city);
                mainWeather.setText(todayWeather.getDayweather());
                maxMin.setText("最高："+todayWeather.getDaytemp()+"℃  最低："+todayWeather.getNighttemp()+"℃");
                mainDayWeather.setText(todayWeather.getDayweather());
                mainDayTemp.setText(todayWeather.getDaytemp());
                mainDayWind.setText(todayWeather.getDaywind()+"风"+todayWeather.getDaypower()+"级");
                mainNightWeather.setText(todayWeather.getNightweather());
                mainNightTemp.setText(todayWeather.getNighttemp());
                mainNightWind.setText(todayWeather.getNightwind()+"风"+todayWeather.getNightpower()+"级");
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleButtons()
    {

        for(int k=0;k<4;k++)
        {
            //当前按下的设置为蓝色，其他恢复
            if(k!=current)
            {
                cities[k].setBackgroundResource(R.drawable.bg_shadow_radius);
            }else{
                cities[current].setBackgroundResource(R.drawable.bg_shadow_radius_pressed);
            }
        }
    }
}