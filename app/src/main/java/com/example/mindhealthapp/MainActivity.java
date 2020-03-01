package com.example.mindhealthapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mindhealthapp.activity.Login;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity {
    private Button logoutbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutbut = findViewById(R.id.logout);
        logoutbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BmobUser.logOut();
                        Toast.makeText(MainActivity.this,"退出成功",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        MainActivity.this.finish();

                    }
                }, 2000);



            }
        });


    }
}

