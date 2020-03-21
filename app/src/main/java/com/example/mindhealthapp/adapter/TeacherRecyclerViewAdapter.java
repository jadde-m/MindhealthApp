package com.example.mindhealthapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.Bean.TeacherItem;
import com.example.mindhealthapp.R;

import java.util.List;

public class TeacherRecyclerViewAdapter extends RecyclerView.Adapter<TeacherRecyclerViewAdapter.ViewHolder> {
    private List<TeacherItem> mycontent;
    int time;
    //private DatepickActivity mParentActivity;
    /*
    初始化函数
     */
    public TeacherRecyclerViewAdapter(List<TeacherItem> mycontent,
                                      int time) {
        this.time = time;
        this.mycontent = mycontent;
    }


    //接口
    public interface OnTeacherClickListener {
        void onClick(int position);
    }

    private OnTeacherClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnTeacherClickListener(OnTeacherClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    //子项布局
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.e("this","我在设置第页面");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_content,parent,false);
        return new ViewHolder(view);
    }

    //子项数据
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.myIdView.setText(mycontent.get(position).getAppointment().getObjectId());
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
        Log.e("this","我在设置第"+position);
    }

    @Override
    public int getItemCount() {
        return mycontent.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView myIdView;
        RadioButton radioButton;
        LinearLayout detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myIdView = itemView.findViewById(R.id.teacherlistinfo);
            radioButton = itemView.findViewById(R.id.radiobuttom);
            detail = itemView.findViewById(R.id.teacherlistdetail);
        }
    }
}
