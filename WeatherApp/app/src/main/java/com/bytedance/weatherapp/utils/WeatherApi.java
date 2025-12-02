package com.bytedance.weatherapp.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.bytedance.weatherapp.bean.WeatherBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherApi {
    private static Handler handler=new Handler(Looper.getMainLooper());
    public static void getWeathers(String cityId,ApiCallback<List<WeatherBean>> callback)
    {
        String url="https://restapi.amap.com/v3/weather/weatherInfo?city="+cityId+"&extensions=all&&key=70c05ae690d5f549b0fc5378fb9ac54c";
        ArrayList<WeatherBean> beanList=new ArrayList();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //解析天气json
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray=jsonObject.getJSONArray("forecasts").getJSONObject(0).getJSONArray("casts");
                        for(int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject item=jsonArray.getJSONObject(i);
                            WeatherBean weatherBean=new WeatherBean();
                            weatherBean.setDate(item.getString("date"));
                            weatherBean.setWeek(item.getString("week"));
                            weatherBean.setDayweather(item.getString("dayweather"));
                            weatherBean.setNightweather(item.getString("nightweather"));
                            weatherBean.setDaytemp(item.getString("daytemp"));
                            weatherBean.setNighttemp(item.getString("nighttemp"));
                            weatherBean.setDaywind(item.getString("daywind"));

                            weatherBean.setNightwind(item.getString("nightwind"));
                            weatherBean.setDaypower(item.getString("daypower"));
                            weatherBean.setNightpower(item.getString("nightpower"));
                            beanList.add(weatherBean);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(beanList);
                            }
                        });
                    }else{
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(new Exception("Invalid Status"));
                            }
                        });
                    }
                } catch (JSONException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public interface ApiCallback<T>{
        void onSuccess(T data);
        void onError(Exception e);
    }
}


