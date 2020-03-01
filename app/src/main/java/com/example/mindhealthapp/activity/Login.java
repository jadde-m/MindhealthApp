package com.example.mindhealthapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindhealthapp.Interface.UserInfo;
import com.example.mindhealthapp.MainActivity;
import com.example.mindhealthapp.R;
import com.google.android.material.snackbar.Snackbar;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class Login extends AppCompatActivity {

    private EditText password,username;
    private Button login,confirm;
    //首次登录初始化模块
    private LinearLayout init;
    private EditText initpass,initconfirmpass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.userid);
        password = findViewById(R.id.userpassword);
        init = findViewById(R.id.initcont);
        initpass = findViewById(R.id.initpassword);
        initconfirmpass = findViewById(R.id.initconfirmpassword);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserInfo user =new UserInfo();
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

                            UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);

                            switch (userInfo.getStatus()){
                                case 0: init.setVisibility(View.VISIBLE);break;
                                case 1:
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    username.setText("");
                                    password.setText("");
                                    startActivity(intent);
                                    break;
                            }
                        }else{
                            //todo 细化提示 123
                            Toast.makeText(Login.this,"登录失败"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //UserInfo userInfo = new UserInfo();
                //userInfo = BmobUser.getCurrentUser(UserInfo.class);
                String initpassword,initconfirmpassword;
                initpassword = initpass.getText().toString();
                initconfirmpassword = initconfirmpass.getText().toString();
                if(initpassword.equals(initconfirmpassword)){
                    UserInfo.updateCurrentUserPassword(password.getText().toString(), initpassword, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                final UserInfo userInfo;
                                userInfo = BmobUser.getCurrentUser(UserInfo.class);
                                userInfo.setStatus(1);
                                userInfo.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(Login.this,"更新成功",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login.this,MainActivity.class));
                                        } else {
                                            Toast.makeText(Login.this,"更新失败",Toast.LENGTH_SHORT).show();
                                            Log.e("error", e.getMessage());
                                        }
                                    }
                                });
                                //Toast.makeText(Login.this,"设置密码成功",Toast.LENGTH_SHORT).show();



                            }else{
                                Toast.makeText(Login.this,"重设密码失败，请重试",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }

            }
        });





    }
}
