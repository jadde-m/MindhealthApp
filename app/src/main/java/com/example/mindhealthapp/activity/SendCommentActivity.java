package com.example.mindhealthapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mindhealthapp.Bean.Comment;
import com.example.mindhealthapp.Bean.Post;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;

public class SendCommentActivity extends AppCompatActivity {
    private TextView commenttitle;
    private EditText commencontent;
    private Button sendbutton;
    private Comment comment = new Comment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_comment_activity);

        initview();

        Intent intent = getIntent();
        Post post = (Post) intent.getExtras().getSerializable("post");
        String title = post.getTitle();
        if(title.length()>15){
            title = title.substring(0,12)+"...";
            Log.e("this",title+"进入初始化");
        }
        commenttitle.setText(title);
        comment.setPost(post);
        comment.setUser(BmobUser.getCurrentUser(UserInfo.class));

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content= String.valueOf(commencontent.getText());
                if(content==""){
                    Toast.makeText(SendCommentActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }else {
                    comment.setContent(content);
                    comment.setDate(BmobDate.createBmobDate("yyyy年MM月dd日  HH:mm:ss", String.valueOf(System.currentTimeMillis())));
                    post.addComments(comment);

                    BmobBatch batch = new BmobBatch();
                    List<BmobObject> commentList= new ArrayList<>();
                    commentList.add(comment);
                    List<BmobObject> postList= new ArrayList<>();
                    postList.add(post);
                    batch.insertBatch(commentList);
                    batch.updateBatch(postList);
                    batch.doBatch(new QueryListListener<BatchResult>() {
                        @Override
                        public void done(List<BatchResult> list, BmobException e) {
                            if(e==null){
                                Toast.makeText(SendCommentActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                                SendCommentActivity.this.finish();
                            }else{
                                Toast.makeText(SendCommentActivity.this,"发送评论失败",Toast.LENGTH_SHORT).show();
                                Log.e("this",""+e.getErrorCode()+e.getMessage());
                            }
                        }
                    });

                }
            }
        });

    }
    //初始化界面
    private void initview() {
        commenttitle = findViewById(R.id.sendcommenttitle);
        commencontent = findViewById(R.id.sendcommentcontent);
        sendbutton = findViewById(R.id.sendcommentbutton);
    }
}
