package com.example.mindhealthapp.Bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Appointment extends BmobObject {
    /*
    * Date 时间
    * UserInfo 咨询师 预约学生
    * int 预约状态 0：未预约 1：已预约 2：已完成 3：已反馈
    *     出席状态 0：未出席 1：已出席
    * String 情况描述 反馈
    *
    *
    */

    private BmobDate date;
    private UserInfo teacher,student;
    private int status,absence;
    private String describe,review;


    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public UserInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(UserInfo teacher) {
        this.teacher = teacher;
    }

    public UserInfo getStudent() {
        return student;
    }

    public void setStudent(UserInfo student) {
        this.student = student;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
