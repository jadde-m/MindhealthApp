package com.example.mindhealthapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.Bean.TimeItem;
import com.example.mindhealthapp.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobDate;

public class TimeRecycleViewAdapter extends RecyclerView.Adapter<TimeRecycleViewAdapter.ViewHolder> {
    private List<TimeItem> mycontent;
    private RecyclerView nextrecycleview;
    private RadioGroup radioGroup;
    BmobDate date;
//接受items
    public TimeRecycleViewAdapter(RecyclerView nextrecycleview, List<TimeItem> items, BmobDate date){
        mycontent = items;
        this.nextrecycleview = nextrecycleview;
        this.date = date;
    };
    @NonNull
    @Override
    //子项布局
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.e("this","try to show time"+ item.getTime()+" count "+item.getCount());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_list_content,parent,false);
        return new ViewHolder(view);
    }

    //接口
    public interface OnTimeClickListener {
        void onClick(int position);
    }

    private OnTimeClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnTimeClickListener(OnTimeClickListener listener) {
        this.listener = listener;
    }





    @Override
    //子项数据
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.e("this","我在设置第"+position);
        TimeItem item = mycontent.get(position);
        Log.e("this","try to show time"+ item.getTime()+" count "+String.valueOf(item.getCount()));
        holder.myIdView.setText(String.valueOf(item.getTime()+9));
        holder.myCountView.setText(String.valueOf(item.getCount()));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }

    @Override
    //返回计数
    public int getItemCount() {
        return mycontent.size();
    }
    /*private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override

            //点击相应函数

            //点击条目 显示详细信息
        //TODO 添加radiobuton
        public void onClick(View view) {
            Log.e("this","列表中有1");
            TimeItem item = (TimeItem) view.getTag();
            Log.e("this","列表中有2");
            //建立下级recycleview视图
            nextrecycleview.setAdapter(new TeacherRecyclerViewAdapter(item.getTeacherItemList(),item.getTime()));

        }
    };*/
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView myIdView;
        TextView myCountView;
        LinearLayout detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myIdView = itemView.findViewById(R.id.timelistcontent);
            myCountView = itemView.findViewById(R.id.timelistcount);
            detail = itemView.findViewById(R.id.timelistdetail);
        }
    }
}
