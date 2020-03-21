package com.example.mindhealthapp.Bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class UserInfo extends BmobUser {
    //  用户名：学号 首次登录密码：身份证号，邮箱、手机号码

    /*自定义
       int 性别 身份证号
       date 注册时间
       string 头像
       int 用户类型 0：管理员，1：学生，2：教师
       int 账号状态 0：新账号，1：老账号，2：禁用
    */
    private int sex,id,status,type;
    private String name;
    private BmobDate date;
    private BmobFile icon;
    private BmobRelation comments;
    private BmobRelation likes;
    private BmobRelation visiters;



    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BmobRelation getComments() {
        return comments;
    }

    public void setComments(BmobRelation comments) {
        this.comments = comments;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public BmobRelation getVisiters() {
        return visiters;
    }

    public void setVisiters(BmobRelation visiters) {
        this.visiters = visiters;
    }
}
