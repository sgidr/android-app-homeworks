package com.bytedance.weatherapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.weatherapp.MainActivity;
import com.bytedance.weatherapp.R;
import com.bytedance.weatherapp.adapter.WeatherAdapter;
import com.bytedance.weatherapp.bean.WeatherBean;
import com.bytedance.weatherapp.utils.WeatherApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private String cityId,city;
    private int cityIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail);
        cityId=getIntent().getStringExtra("cityId");
        cityIndex=getIntent().getIntExtra("cityIndex",0);
        city=cityId.equals("110101")?"北京":cityId.equals("310100")?"上海":cityId.equals("440100")?"广州":"深圳";
        ((TextView)findViewById(R.id.main_city_title)).setText(city);
        findViewById(R.id.detail_city).setOnClickListener(v->{
            Intent intent=new Intent(DetailActivity.this,MainActivity.class);
            intent.putExtra("cityIndex",cityIndex);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
        setupRecyclerView();
        loadWeathers();

    }

    public void loadWeathers()
    {
        WeatherApi.getWeathers(cityId,new WeatherApi.ApiCallback<List<WeatherBean>>() {
            @Override
            public void onSuccess(List<WeatherBean> data) {
                //创建Adapter并设置给RecyclerView
                WeatherAdapter adapter = new WeatherAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(DetailActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setupRecyclerView()
    {
        recyclerView=findViewById(R.id.recyclerview);
        //设置布局管理器（这里用线性布局，垂直方向）
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}