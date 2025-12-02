package com.bytedance.loginapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.loginapp.activity.UserActivity;
import com.bytedance.loginapp.data.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity{
    private MyDatabaseHelper myDatabaseHelper;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        myDatabaseHelper=new MyDatabaseHelper(this);
        usernameEditText=findViewById(R.id.editTextTextEmailAddress);
        passwordEditText=findViewById(R.id.editTextTextEmailAddress1);


        findViewById(R.id.btn_login).setOnClickListener(v->{
            String username=usernameEditText.getText().toString();
            String password=passwordEditText.getText().toString();
            if(username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"邮箱或密码为空，请输入",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!myDatabaseHelper.checkLogin(username,password))
            {
                Toast.makeText(this,"邮箱或者密码错误",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
            }

        });
        findViewById(R.id.login_wechat).setOnClickListener(v->{
            Toast.makeText(this,"微信登录",Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.login_apple).setOnClickListener(v->{
            Toast.makeText(this,"Apple登录",Toast.LENGTH_SHORT).show();
        });

    }

}