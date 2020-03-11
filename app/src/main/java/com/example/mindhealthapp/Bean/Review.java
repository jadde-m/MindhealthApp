package com.example.mindhealthapp.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class Review extends BmobObject {
    //评论日期
    private BmobDate date;
    //发送者 接收者
    private UserInfo sender,reciver;
    //评论内容
    private String content;

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
    }

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public UserInfo getReciver() {
        return reciver;
    }

    public void setReciver(UserInfo reciver) {
        this.reciver = reciver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
