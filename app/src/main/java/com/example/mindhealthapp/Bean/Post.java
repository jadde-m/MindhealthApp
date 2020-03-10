package com.example.mindhealthapp.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject {
    //发送者
    private UserInfo sender;
    //标题
    private String title;
    //内容
    private String content;
    //日期
    private BmobDate date;
    //点赞
    private BmobRelation likes;
    //浏览者
    private BmobRelation visiters;
    //点赞数，浏览数
    private int like,visiter;

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobDate getDate() {
        return date;
    }

    public void setDate(BmobDate date) {
        this.date = date;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getVisiter() {
        return visiter;
    }

    public void setVisiter(int visiter) {
        this.visiter = visiter;
    }
}
