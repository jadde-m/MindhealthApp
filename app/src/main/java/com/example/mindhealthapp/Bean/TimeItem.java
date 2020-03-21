package com.example.mindhealthapp.Bean;

import java.util.ArrayList;
import java.util.List;

public class TimeItem {
    private List<TeacherItem> teacherItemList;
    private int count;
    private int time;
    public TimeItem(int time, int count/*, List<TeacherItem> teacherItemList*/){
        this.time = time;
        this.count = count;
        teacherItemList = new ArrayList<>();

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void addCount(){
        count = count + 1;
    }
    public List<TeacherItem> getTeacherItemList() {
        return teacherItemList;
    }

    public void setTeacherItemList(List<TeacherItem> teacherItemList) {
        this.teacherItemList = teacherItemList;
    }

    public void addTeacherItem(TeacherItem teacherItem) {
        teacherItemList.add(teacherItem);
    }

    /*public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }*/
}
