package com.example.mindhealthapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mindhealthapp.Bean.Appointment;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;
import com.example.mindhealthapp.activity.DatepickActivity;
import com.example.mindhealthapp.activity.Splash;

import java.util.zip.Inflater;

import cn.bmob.v3.BmobUser;

public class AppointFragment extends Fragment {
    private Button reserve;
    private EditText name,phone,describe;
    private Appointment appointment;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appoint,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo bmobUser = BmobUser.getCurrentUser(UserInfo.class);

        appointment = new Appointment();
        //按钮
        reserve = getActivity().findViewById(R.id.appointreserve);
        //预约者名字
        name = getActivity().findViewById(R.id.appointname);
        //电话
        phone = getActivity().findViewById(R.id.appointphone);
        //问题描述
        describe = getActivity().findViewById(R.id.appointdescrib);

        name.setText(bmobUser.getUsername());
        phone.setText(bmobUser.getMobilePhoneNumber());
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText().equals("")){
                    Toast.makeText(getActivity(),"请输入电话",Toast.LENGTH_SHORT).show();
                }else{
                    //转到选择日期时间页，传递appointment信息
                    appointment.setStudent(bmobUser);
                    appointment.setPhone(phone.getText().toString());
                    appointment.setDescribe(describe.getText().toString());
                    Intent intent = new Intent(getActivity(), DatepickActivity.class);
                    intent.putExtra("appointinfo",appointment);
                    startActivity(intent);
                }
            }
        });

    }
}
