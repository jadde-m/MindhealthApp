package com.example.mindhealthapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.mindhealthapp.R;
import com.example.mindhealthapp.adapter.FragmentAdapter;
import com.example.mindhealthapp.fragment.AppointFragment;
import com.example.mindhealthapp.fragment.MainFragment;
import com.example.mindhealthapp.fragment.PostsFragment;
import com.example.mindhealthapp.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {
    private Button logoutbut;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home: {
                    viewPager.setCurrentItem(0);
                    break;
                }
                case R.id.navigation_dashboard: {
                    viewPager.setCurrentItem(1);
                    break;
                }
                case R.id.navigation_notifications: {
                    viewPager.setCurrentItem(2);
                    break;
                }
                case R.id.navigation_mine: {
                    viewPager.setCurrentItem(3);
                    break;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO 看是否每个activity需要初始化
        Bmob.initialize(this, "d678f27fde3ba99626b41d550316f7ba");
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.nav_view);
        initView();

    }

    private void initView() {


        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new AppointFragment());
        fragments.add(new PostsFragment());
        fragments.add(new SettingFragment());


        FragmentAdapter fragmentAdapter = new FragmentAdapter(fragments,getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        /*if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //将滑动到的页面对应的 menu 设置为选中状态
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}





















 /*logoutbut = findViewById(R.id.logout);
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
        });*/
