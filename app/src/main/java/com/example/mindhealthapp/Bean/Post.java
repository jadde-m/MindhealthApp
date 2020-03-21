package com.example.mindhealthapp.Bean;

import android.util.Log;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
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
    //浏览者
    private BmobRelation comments;
    //点赞数，浏览数
    private int like,visiter,comment;



    public Post(){
        likes = new BmobRelation();
        visiters = new BmobRelation();
        comments = new BmobRelation();
        like = 0;
        visiter = 0;
        comment = 0;
    }


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

    public void addLike() {
        like++;
    }

    public void deleteLike() {
        like--;
    }

    public void addVisiter() {
        visiter++;
    }
/*
    public void deleteVisiter() {
        visiter--;
    }*/


    public void addComment() {
        comment++;
    }

    public void deleteComment() {
        comment--;
    }

    public void removeComments(Comment comment) {
        this.comments.remove(comment);
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
    }

    public void removeVisiters(UserInfo user) {
        visiters.remove(user);
    }

    public void addVisiters(UserInfo user) {
        visiters.add(user);
    }

    public void removeLikes(UserInfo user) {
        likes.remove(user);
    }

    public void addLikes(UserInfo user) {
        likes.add(user);
    }

    public Boolean searchlike(UserInfo user){

        Log.e("this","relation中有"+likes.getObjects().size()+"个");
        if(likes.getObjects().contains(new BmobPointer(user))){

            Log.e("this","relation中有");
            return true;
        }else {
            Log.e("this","relation中无");
            return false;
        }
    }


}
