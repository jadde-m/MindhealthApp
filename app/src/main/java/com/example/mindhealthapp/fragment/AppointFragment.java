package com.example.mindhealthapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_appoint,null,false);
        //按钮
        reserve = view.findViewById(R.id.appointreserve);
        //预约者名字
        name = view.findViewById(R.id.appointname);
        //电话
        phone = view.findViewById(R.id.appointphone);
        //问题描述
        describe = view.findViewById(R.id.appointdescrib);
        UserInfo bmobUser = BmobUser.getCurrentUser(UserInfo.class);

        //按钮
        reserve = view.findViewById(R.id.appointreserve);
        //预约者名字
        name = view.findViewById(R.id.appointname);
        //电话
        phone = view.findViewById(R.id.appointphone);
        //问题描述
        describe = view.findViewById(R.id.appointdescrib);

        name.setText("jade");
        phone.setText(bmobUser.getMobilePhoneNumber());
        phone.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(11)
        });
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone.getText().equals("")){
                    Toast.makeText(getActivity(),"请输入电话",Toast.LENGTH_SHORT).show();
                }else{
                    //转到选择日期时间页，传递appointment信息
                    /*intent传值 studentid 学生用户标识号
                                phone 手机号
                                describe 描述*/
                    Intent intent = new Intent(getActivity(), DatepickActivity.class);
                    //intent.putExtra("studendid",bmobUser.getObjectId());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("student",bmobUser);
                    bundle.putString("studendphone",phone.getText().toString());
                    bundle.putString("studenddescribe",describe.getText().toString());
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            }
        });
        return view;




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
