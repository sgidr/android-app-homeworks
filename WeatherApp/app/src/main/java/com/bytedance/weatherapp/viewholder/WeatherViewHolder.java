package com.bytedance.weatherapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.weatherapp.R;
import com.bytedance.weatherapp.bean.WeatherBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * create by WUzejian on 2025/11/17
 */
public class WeatherViewHolder extends RecyclerView.ViewHolder {
    TextView detail_day_text;
    TextView detail_day_time;
    TextView detail_weather_txt;
    TextView detail_min_temp;
    TextView detail_max_temp;


    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        // 绑定视图ID
        detail_day_text = itemView.findViewById(R.id.detail_day_text);
        detail_day_time = itemView.findViewById(R.id.detail_day_time);
        detail_weather_txt = itemView.findViewById(R.id.detail_weather_txt);
        detail_min_temp = itemView.findViewById(R.id.detail_min_temp);
        detail_max_temp = itemView.findViewById(R.id.detail_max_temp);
    }

    // 绑定数据到视图
    public void bindData(WeatherBean userBean) throws ParseException {
        Date now=new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate=simpleDateFormat.parse(simpleDateFormat.format(now));
        Date weatherDate=simpleDateFormat.parse(userBean.getDate());
        long cross=weatherDate.getTime()-nowDate.getTime();
        //计算日差
        int dayCross= Math.toIntExact(cross/1000 / 60 / 60 / 24);
        String dateText,weatherText=userBean.getDayweather();
        switch (dayCross){
            case 0:
                dateText="今天";
                break;
            case 1:
                dateText="明天";
                break;
            default:
                switch (Integer.parseInt(userBean.getWeek())) {
                    case 1:
                        dateText = "星期一";
                        break;
                    case 2:
                        dateText="星期二";
                        break;
                    case 3:
                        dateText="星期三";
                        break;
                    case 4:
                        dateText="星期四";
                        break;
                    case 5:
                        dateText="星期五";
                        break;
                    case 6:
                        dateText="星期六";
                        break;
                    case 7:
                        dateText="星期日";
                        break;
                    default:
                        dateText="未知";
                        break;
                }
                break;
        }
        weatherText=weatherText.equals("晴")?"☀晴":weatherText.equals("多云")?"⛅多云"
                :weatherText.equals("小雨")?"\uD83C\uDF27小雨":weatherText.equals("阴")?"⛅阴":"\uD83C\uDF24\uFE0F"+weatherText;
        detail_day_text.setText(dateText);
        detail_day_time.setText(userBean.getDate().substring(5));
        detail_weather_txt.setText(weatherText);
        detail_min_temp.setText(userBean.getNighttemp()+"℃");
        detail_max_temp.setText(userBean.getDaytemp()+"℃");
    }
}
