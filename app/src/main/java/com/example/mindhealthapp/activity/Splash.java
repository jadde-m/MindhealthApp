package com.example.mindhealthapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindhealthapp.MainActivity;
import com.example.mindhealthapp.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actity_splash);

        Bmob.initialize(this, "d678f27fde3ba99626b41d550316f7ba");
        //延时操作
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
                if(bmobUser!=null){
                    Toast.makeText(Splash.this,"您已登录",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Splash.this,MainActivity.class));
                }else{
                    Toast.makeText(Splash.this,"请登录",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Splash.this,Login.class));
                }
            }
        }, 2000);


    }

}
