package com.example.mindhealthapp.Bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Type extends BmobObject {
    //学生、教师列表
    private BmobRelation student,teacher;

    public BmobRelation getStudent() {
        return student;
    }

    public void setStudent(BmobRelation student) {
        this.student = student;
    }

    public BmobRelation getTeacher() {
        return teacher;
    }

    public void setTeacher(BmobRelation teacher) {
        this.teacher = teacher;
    }
}
