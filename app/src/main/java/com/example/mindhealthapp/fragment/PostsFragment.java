package com.example.mindhealthapp.fragment;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.mindhealthapp.Bean.Post;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;
import com.example.mindhealthapp.activity.MainActivity;
import com.example.mindhealthapp.activity.PostDetailActivity;
import com.example.mindhealthapp.activity.SendCommentActivity;
import com.example.mindhealthapp.adapter.PostsAdapter;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.UpdateListener;

public class PostsFragment extends Fragment {
    private StaggeredGridLayoutManager postsLayoutManager;
    private PostsAdapter postsAdapter;
    private RecyclerView postRecyclerView;
    private SmartRefreshLayout smartRefreshLayout;
    //记录刷新行数
    private int n;
    private List<Post> posts = new ArrayList<>();
    private UserInfo user = BmobUser.getCurrentUser(UserInfo.class);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postsr,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        //刷新
        smartRefreshLayout = getActivity().findViewById(R.id.postrefresh);
        smartRefreshLayout.setRefreshHeader(new BezierCircleHeader(getActivity()));

        //下拉刷新
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e("this","进入刷新"+posts.size());
                Refresh(0);
            }
        });
        //加载更多
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e("this","进入加载更多"+posts.size());
                Refresh(posts.size());
            }
        });
        initUserLike();
        smartRefreshLayout.autoRefresh();



       /* BmobQuery<Post> postQuery = new BmobQuery<Post>();
        postQuery.order("-date");
        postQuery.setLimit(5).setSkip(n).findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        posts.addAll( n ,list);
                        postsAdapter.changelist(posts);
                    }else {
                        Toast.makeText(getActivity(),"没有内容",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.e("this","刷新失败，错误是"+e.getErrorCode()+e.getMessage());
                    Toast.makeText(getActivity(),"查找失败",Toast.LENGTH_LONG).show();
                }
            }
        });
        BmobQuery<Post> likeQuery = new BmobQuery<Post>();

        likeQuery.addWhereRelatedTo("likes",new BmobPointer(user));
        likeQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    Log.e("this","查找成功，共喜欢"+list.size()+"个帖子");
                    postsAdapter.changeLike(list);
                }else {
                    Log.e("this","查找失败");
                }
            }
        });
*/



        postRecyclerView = getActivity().findViewById(R.id.postsrecycleview);
        //设置LayoutManager
        postsLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        postRecyclerView.setLayoutManager(postsLayoutManager);
        postsAdapter = new PostsAdapter(posts,user);


        //点赞按钮功能
        postsAdapter.setOnLikeClickListener(new PostsAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(View view, int position,int type) {
                BmobRelation bmobRelation = new BmobRelation();
                Post post = posts.get(position);
                if(type==0) {
                    post.addLike();
                    bmobRelation.add(post);
                    user.setLikes(bmobRelation);
                } else if(type == 1){
                    post.deleteLike();
                    bmobRelation.remove(post);
                    user.setLikes(bmobRelation);
                }
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            post.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.e("this",post.getTitle()+"喜欢成功"+type);
                                        postsAdapter.changeLike(post);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        //评论按钮功能
        postsAdapter.setOnCommentClickListener(new PostsAdapter.OnCommentClickListener() {
            @Override
            public void onCommentClick(View view, int position) {
                Intent intent = new Intent(getActivity(), SendCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",posts.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });

        //内容点击按钮
        postsAdapter.setOnDetailClickListener(new PostsAdapter.OnDetailClickListener() {
            @Override
            public void onDetailClick(View view, int position,int like) {
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("post",posts.get(position));
                intent.putExtras(bundle);
                intent.putExtra("like",like);
                startActivity(intent);
            }
        });


        postRecyclerView.setAdapter(postsAdapter);


        //添加流程
        /*List<BmobObject> insert = new ArrayList<>();
        for (int i =0;i<20;i++){
            Post post = new Post();
            post.setTitle("帖子"+i);
            post.setContent("帖子内容是"+i);
            post.setDate(BmobDate.createBmobDate("yyyy-MM-dd HH:mm:ss","2020-03-"+i+" 15:05:50"));
            post.setSender(BmobUser.getCurrentUser(UserInfo.class));
            posts.add(post);
            insert.add(post);
        }
        new BmobBatch().insertBatch(insert).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e==null){
                    Log.e("this","已添加");
                    Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"添加失败",Toast.LENGTH_SHORT).show();
                }
            }
        });*/





    }

    @Override
    public void onResume() {
        super.onResume();
        initUserLike();/*
        smartRefreshLayout.autoRefresh();*/
    }

    //刷新
    private void Refresh(int n) {
        BmobQuery<Post> postQuery = new BmobQuery<Post>();
        postQuery.order("-date");
        if(n==0&&(posts.size()>0)){
            postQuery.addWhereGreaterThan("date",posts.get(1));
        }
        Log.e("this","刷新前，跳过长度是"+posts.size());
        postQuery.setLimit(5).setSkip(n).findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    if(list.size()!=0){
                        smartRefreshLayout.finishRefresh(500);
                        smartRefreshLayout.finishLoadMore(500);
                        posts.addAll( n ,list);
                        postsAdapter.changelist(posts);
                    }else {
                        smartRefreshLayout.finishRefresh(false);
                        smartRefreshLayout.finishLoadMore(false);
                        Toast.makeText(getActivity(),"没有新内容",Toast.LENGTH_LONG).show();
                    }
                }else {
                    smartRefreshLayout.finishRefresh(false);
                    smartRefreshLayout.finishLoadMore(false);
                    Log.e("this","刷新失败，错误是"+e.getErrorCode()+e.getMessage());
                    Toast.makeText(getActivity(),"查找失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //创建activity时初始化用户点赞列表
    private void initUserLike() {
        BmobQuery<Post> likeQuery = new BmobQuery<Post>();

        likeQuery.addWhereRelatedTo("likes",new BmobPointer(user));
        likeQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    postsAdapter.changeLike(list);
                }else {
                    Log.e("this","查找失败");
                }
            }
        });
    }
}
