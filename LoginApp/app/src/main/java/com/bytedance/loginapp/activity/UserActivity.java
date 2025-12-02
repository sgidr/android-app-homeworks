package com.bytedance.loginapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.loginapp.R;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    private SharedPreferences mSharedPreferences;
    private TextView usernameTextView,welcomeTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user);
        mSharedPreferences=getSharedPreferences(TAG,MODE_PRIVATE);
        SharedPreferences.Editor editor=mSharedPreferences.edit();
        editor.putString("username","张三");
        editor.putString("welcome","欢迎张三来到信息App");
        editor.apply();

        usernameTextView=findViewById(R.id.username);
        welcomeTextView=findViewById(R.id.welcome);
        usernameTextView.setText(mSharedPreferences.getString("username","用户名"));
        welcomeTextView.setText(mSharedPreferences.getString("welcome","欢迎来到信息App"));
        setItem(R.id.row_profile,R.drawable.row_profile,"个人信息");
        setItem(R.id.row_favorites,R.drawable.row_favorite,"我的收藏");
        setItem(R.id.row_history,R.drawable.row_history,"浏览历史");
        setItem(R.id.row_settings,R.drawable.row_setting,"设置");
        setItem(R.id.row_about,R.drawable.row_ablout,"关于我们");
        setItem(R.id.row_feedback,R.drawable.row_feedback,"意见反馈");
    }

    private void setItem(int itemId,int iconId,String text){
        View item=findViewById(itemId);
        ImageView icon=item.findViewById(R.id.iv_icon);
        TextView title=item.findViewById(R.id.tv_title);
        icon.setImageResource(iconId);
        title.setText(text);
        item.setOnClickListener(v->{
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        });
    }
}
