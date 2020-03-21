package com.example.mindhealthapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mindhealthapp.Bean.Comment;
import com.example.mindhealthapp.Bean.Post;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;
import com.example.mindhealthapp.adapter.PostDetailAdapter;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class PostDetailActivity extends AppCompatActivity {

    private List<Comment> comments = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private PostDetailAdapter postDetailAdapter;
    private RecyclerView postDetailRecycleView;
    private TextView title;
    private Button postLikeButton,postReturnButton,postCommentButton;
    private int type;
    private UserInfo user = BmobUser.getCurrentUser(UserInfo.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //初始化界面
        initView();


        Intent intent = getIntent();
        Post post = (Post) intent.getExtras().getSerializable("post");
        //设置标题
        title.setText(post.getTitle());

        //设置点赞
        type = intent.getIntExtra("like",0);
        if(type==1){
            postLikeButton.setBackgroundResource(R.drawable.ic_like);
        }else {
            postLikeButton.setBackgroundResource(R.drawable.ic_unlike);
        }


        //初始化adapter
        postDetailAdapter = new PostDetailAdapter(comments,post);


            //刷新
        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(this));

                //加载更多
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Refresh(post);
            }
        });
        smartRefreshLayout.autoLoadMore();


            //按钮事件
                //点赞按钮
        postLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobRelation bmobRelation = new BmobRelation();
                Drawable orgin = postLikeButton.getBackground();
                if(type==0) {
                    post.addLike();
                    bmobRelation.add(post);
                    user.setLikes(bmobRelation);
                    postLikeButton.setBackgroundResource(R.drawable.ic_like);
                } else if(type == 1){
                    post.deleteLike();
                    bmobRelation.remove(post);
                    user.setLikes(bmobRelation);
                    postLikeButton.setBackgroundResource(R.drawable.ic_unlike);
                }
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null)
                            post.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null) Log.e("this",post.getTitle()+"喜欢成功"+type);
                                    else postLikeButton.setBackground(orgin);
                                }
                            });
                        else
                            postLikeButton.setBackground(orgin);
                    }
                });
            }
        });

                //评论按钮
        postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetailActivity.this, SendCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",post);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

                //返回按钮
        postReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDetailActivity.this.finish();
            }
        });





        //绑定adapter
        postDetailRecycleView.setAdapter(postDetailAdapter);






    }


    //初始化界面组件
    private void initView() {
        title = findViewById(R.id.postdetailtitle);
        smartRefreshLayout = findViewById(R.id.postdetailrefresh);
        postDetailRecycleView = findViewById(R.id.postdetailrecycleview);
        postLikeButton = findViewById(R.id.postdetaillike);
        postCommentButton = findViewById(R.id.postdetailcomment);
        postReturnButton = findViewById(R.id.postdetailreturn);
    }


    //刷新留言方法
    private void Refresh(Post post) {
        BmobQuery<Comment> query = new BmobQuery<>();
        if(comments.size()>0){
            query.addWhereGreaterThan("date",comments.get(1).getDate());
        }
        query.order("date");
        query.addWhereRelatedTo("comment",new BmobPointer(post));
        query.setLimit(10).setSkip(comments.size()).findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        smartRefreshLayout.finishLoadMore(500);
                        comments.addAll(list);
                        postDetailAdapter.changeList(comments);
                    }else {
                        smartRefreshLayout.finishLoadMore(false);
                        Toast.makeText(PostDetailActivity.this,"没有更多了",Toast.LENGTH_LONG).show();
                    }
                }else {
                    smartRefreshLayout.finishLoadMore(false);
                    Log.e("this",e.getErrorCode()+e.getMessage());
                }
            }
        });


    }
}
