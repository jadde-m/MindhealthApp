package com.example.mindhealthapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindhealthapp.Interface.UserInfo;
import com.example.mindhealthapp.MainActivity;
import com.example.mindhealthapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {

    private EditText password,username;
    private Button login,register;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.userid);
        password = findViewById(R.id.userpassword);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo user =new UserInfo();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                /*登录功能
                    成功登录->检测是否首次登录->首次登录，转到信息填写页
                                            ->非首次登录，转到主页
                    */
                user.login(new SaveListener<UserInfo>() {

                    @Override
                    public void done(UserInfo o, BmobException e) {
                        if(e==null){

                            Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                            username.setText("");
                            password.setText("");

                            startActivity(new Intent(Login.this, MainActivity.class));
                        }else{
                            //todo 细化提示 123
                            Toast.makeText(Login.this,"登录失败"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });




    }
}
