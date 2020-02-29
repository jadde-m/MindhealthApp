package com.example.mindhealthapp.Interface;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public class UserInfo extends BmobUser {
    //  用户名：学号 首次登录密码：身份证号，邮箱、手机号码

    /*自定义
       int 年龄 性别 身份证号
       date 注册时间
       string 专业 头像
       int 用户类型 0：管理员，1：学生，2：教师
       int 账号状态 0：未登录，1：修改过个人信息，2：禁用
    */
    private int sex,age,status,type;
    private String major;
    private BmobDate date;
    private BmobFile icon;



    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
