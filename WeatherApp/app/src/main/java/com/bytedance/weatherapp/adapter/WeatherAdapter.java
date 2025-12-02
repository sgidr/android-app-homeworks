package com.bytedance.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.weatherapp.R;
import com.bytedance.weatherapp.bean.WeatherBean;
import com.bytedance.weatherapp.viewholder.WeatherViewHolder;

import java.text.ParseException;
import java.util.List;

/**
 * create by WUzejian on 2025/11/17
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
    private List<WeatherBean> mweatherList;

    // 构造方法：接收数据集
    public WeatherAdapter(List<WeatherBean> weatherList) {
        this.mweatherList = weatherList;
    }

    /**
     * 第一步：Item创建ViewHolder（加载item布局）
     *
     * @return
     */
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_day, parent, false);
        //创建返回的ViewHolder
        return new WeatherViewHolder(itemRootView);
    }

    /**
     * // 第二步：绑定数据到ViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        //绑定数据
        //当前位置的数据
        WeatherBean weatherBean = mweatherList.get(position);
        // 调用ViewHolder的bindData方法绑定数据
        try {
            holder.bindData(weatherBean);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return mweatherList != null ? mweatherList.size() : 0;
    }

    // 数据更新（后续刷新列表用）
    public void updateData(List<WeatherBean> newweatherList) {
        this.mweatherList = newweatherList;
        // 通知RecyclerView数据已变更
        notifyDataSetChanged();
    }
}
