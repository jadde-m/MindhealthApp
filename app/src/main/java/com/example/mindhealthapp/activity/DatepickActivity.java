package com.example.mindhealthapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.Bean.Appointment;
import com.example.mindhealthapp.Bean.TeacherItem;
import com.example.mindhealthapp.Bean.TimeItem;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;
import com.example.mindhealthapp.adapter.TeacherRecyclerViewAdapter;
import com.example.mindhealthapp.adapter.TimeRecycleViewAdapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static java.util.Calendar.getInstance;


public class DatepickActivity extends AppCompatActivity {
    //预约条目信息
    //日期信息
    private BmobDate bmobDate;
    //日期显示条
    private TextView datetext;
    private int myear,mmonth,mdm,mdw;
    private SimpleDateFormat simpleDateFormat;
    private String text;
    /*private Map<Integer,TimeItem> hashMap = new HashMap<Integer,TimeItem>();*/
    static RecyclerView teacherlistView,timelistView;
    //记录选择教师位置
    private Appointment selectappointment;
    //标志预约是否可选 1：可选 0：不可选
    private int selectflag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pickdate);
        //设置日期
        datetext = findViewById(R.id.appointchosentime);
        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Calendar calendar = getInstance();
        myear = calendar.get(Calendar.YEAR);
        mmonth = calendar.get(Calendar.MONTH)+1;
        mdm = calendar.get(Calendar.DAY_OF_MONTH);
        mdw = calendar.get(Calendar.DAY_OF_WEEK);
        datetext.setText(myear+"年"+mmonth+"月"+mdm+"日  星期"+mdw);
        //设置日期选择
        Bmob.initialize(this, "d678f27fde3ba99626b41d550316f7ba");
        //设置recycleview
        timelistView = findViewById(R.id.time_list);
        assert timelistView != null;
        teacherlistView = findViewById(R.id.teacher_list);
        assert teacherlistView !=null;
        //查询数据

        try {
            bmobDate = new BmobDate(simpleDateFormat.parse(myear+"年"+mmonth+"月"+mdm+"日 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        InitHashmap(bmobDate);



        //TODO 添加功能

        /*for(int i = 0;i<8;i++){
            try {

                addappointment( new BmobDate(simpleDateFormat.parse(myear+"年"+mmonth+"月"+(mdm+i)+"日 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }*/



        //按钮 提交预约数据
       /* reserve = findViewById(R.id.reservebutton);
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectflag==0){
                    updateappointment(selectappointment,getIntent().getExtras());
                }else {

                    Log.e("this","所选日期不可选，请重新选择");
                }

            }
        });*/




    }

    /*private void addappointment(BmobDate bmobDate) {
        List<BmobObject> appointments = new ArrayList<>();
        for(int i=0;i<20;i++){
            Appointment appointment = new Appointment();
            appointment.setDate(bmobDate);
            appointment.setTime( ((int)(Math.random()*9)%9)+9);
            appointment.setTeacher(BmobUser.getCurrentUser(UserInfo.class));
            appointment.setStatus(0);
            appointments.add(appointment);
        }
        new BmobBatch().insertBatch(appointments).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e==null){
                    Toast.makeText(DatepickActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    Log.e("this","添加成功"+bmobDate.getDate().toString());
                }else{
                    Toast.makeText(DatepickActivity.this,"添加失败"+e.getErrorCode()+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    private void updateappointment(Appointment appointment, Bundle bundle) {

        UserInfo userInfo = (UserInfo) bundle.getSerializable("student");
        appointment.setStudent(userInfo);
        appointment.setStatus(1);
        appointment.setPhone(bundle.getString("studendphone"));
        appointment.setDescribe(bundle.getString("studenddescribe"));
        appointment.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    //TODO：写对话框
                    Toast.makeText(DatepickActivity.this,"预约成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DatepickActivity.this,"预约失败",Toast.LENGTH_SHORT).show();
                    Log.e("this","预约失败"+e.getErrorCode()+e.getMessage());
                }
            }
        });


    }

    //日期点击功能
    public void onclick(View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myear = year;
                mmonth = month;
                mdm = dayOfMonth;
                text =  myear + "年" + (mmonth + 1) + "月" + mdm + "日";
                try {
                    Calendar cal = Calendar.getInstance();
                    Date date=simpleDateFormat.parse(text+" 00:00:00");
                    bmobDate = new BmobDate(date);
                    cal.setTime(date);
                    mdw = cal.get(Calendar.DAY_OF_WEEK);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //TODO 细化星期描述
                datetext.setText(text+"  星期"+mdw);
                //doinit(bmobDate);
               /* TimeContent timeContent = new TimeContent();
                timeContent.initItem("date",bmobDate);
                timelistView.setAdapter(new TimeRecycleViewAdapter(teacherlistView,timeContent.getItems(),bmobDate));*/

                try {
                    InitHashmap(bmobDate);
                } catch (Exception e) {

                    e.printStackTrace();
                }
                /*Toast.makeText(DatepickActivity.this,timeContent.getE(),Toast.LENGTH_LONG).show();*/
            }
        },myear,mmonth-1,mdm);
        DatePicker datePicker= datePickerDialog.getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis()+60*60*24*14*1000);
        datePicker.setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    private void InitHashmap(BmobDate bmobDate) {
        /*Map<String,TimeItem> hashMap = new HashMap<String,TimeItem>();*/
        Log.e("this",bmobDate.getDate()+"进入初始化");
        List<TimeItem> timelist = new ArrayList<>();
        BmobQuery<Appointment> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("date",bmobDate);
        //统计各时段分别有多少记录条目

        for(int i = 0;i<9;i++){
            TimeItem timeItem = new TimeItem(i,0);
            timelist.add(timeItem);
            Log.e("this",bmobDate.getDate().toString()+"进入初始化");
        }

        bmobQuery.findObjects(new FindListener<Appointment>() {
            @Override
            public void done(List<Appointment> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        int i = 0;
                        /*for(int i = 1; i < list.size() ; i++)*/
                        for(Appointment appointment:list){
                            i++;
                            //*Appointment apppoinment = list.get(i);*//*
                            int time = appointment.getTime();
                            TimeItem timeItem = timelist.get(time-9);
                            Log.e("this",bmobDate.getDate().toString()+"已初始化");
                            timeItem.addCount();

                            Log.e("this",bmobDate.getDate().toString()+"已加数"+timeItem.getCount());
                            UserInfo teacher = appointment.getTeacher();

                            Log.e("this",bmobDate.getDate().toString()+"获得教师id"+teacher.getObjectId()+"name"+teacher.getUsername());
                            //TODO:改成教师姓名
                            TeacherItem teacherItem = new TeacherItem(appointment,teacher.getUsername());

                            timeItem.addTeacherItem(teacherItem);

                        }
                        //初始化Timeadapter
                        TimeRecycleViewAdapter timeRecycleViewAdapter = new TimeRecycleViewAdapter(teacherlistView,timelist,bmobDate);
                        timeRecycleViewAdapter.setOnTimeClickListener(new TimeRecycleViewAdapter.OnTimeClickListener() {
                            @Override
                            public void onClick(int position) {
                                TimeItem item = timelist.get(position);
                                Log.e("this","列表中有2"+item.getTeacherItemList().size());
                                //初始化teacheradapter
                                teacherlistView.setAdapter(initteacher(item));

                                Log.e("this","应该设置完成了");
                            }
                        });


                        timelistView.setAdapter(timeRecycleViewAdapter);
                        teacherlistView.setAdapter(initteacher(timelist.get(0)));

                        //teacherlistView.setAdapter(timeRecycleViewAdapter);
                        Log.e("this","嘀嘀嘀");

                    }else{
                        Log.e("this","无结果"+bmobDate.toString());
                    }
                }else{
                    //查找出错
                    Log.e("this","查找出错"+e.getErrorCode()+e.getMessage());
                }
            }
        });





    }

    private TeacherRecyclerViewAdapter initteacher(TimeItem item) {
        TeacherRecyclerViewAdapter teacherRecyclerViewAdapter = new TeacherRecyclerViewAdapter(item.getTeacherItemList(),item.getTime());
        teacherRecyclerViewAdapter.setOnTeacherClickListener(new TeacherRecyclerViewAdapter.OnTeacherClickListener() {
            @Override
            public void onClick(int i) {

                selectappointment = item.getTeacherItemList().get(i).getAppointment();
                //TODO:细化提示！！！！
                String info = "您选择的是"+selectappointment.getObjectId();
                selectflag = selectappointment.getStatus();
                AlertDialog.Builder builder = new AlertDialog.Builder(DatepickActivity.this).setTitle("确认").setMessage(info).
                        setPositiveButton("确定预约", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (selectflag==0){
                            updateappointment(selectappointment,getIntent().getExtras());
                        }else {
                            Log.e("this","所选日期不可选，请重新选择"+selectflag);
                        }
                    }
                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();

            }
        });
        return teacherRecyclerViewAdapter;
    }


}
