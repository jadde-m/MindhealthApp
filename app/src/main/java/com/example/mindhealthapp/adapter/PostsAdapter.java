package com.example.mindhealthapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.Bean.Post;
import com.example.mindhealthapp.Bean.UserInfo;
import com.example.mindhealthapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private UserInfo user;
    private List<Post> posts = new ArrayList<>();
    private HashMap<String, Integer> status = new HashMap<>();
    private HashMap<String, Integer> like = new HashMap<>();
    //初始化声明
    public PostsAdapter(List<Post> posts , UserInfo user ){
        this.user = user;
        this.posts = posts;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_list_content,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        like.putIfAbsent(post.getObjectId(),0);
        if(like.get(post.getObjectId())==1){
            Log.e("this","hashmap中有"+post.getTitle()+"种类是like"+like.size()+like.get(post.getObjectId()));
            holder.postLikeButton.setBackgroundResource(R.drawable.ic_like);
        }else if(like.get(post.getObjectId())==0){
            Log.e("this","hashmap中有"+post.getTitle()+"种类是unlike"+like.size()+like.get(post.getObjectId()));
            holder.postLikeButton.setBackgroundResource(R.drawable.ic_unlike);
        }


        holder.postLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnLikeClickListener.onLikeClick(view,holder.getLayoutPosition(),like.get(post.getObjectId()));
            }
        });

        //设置随机高度
        if(!status.containsKey(post.getObjectId())){
            Log.e("this",post.getTitle()+post.getObjectId()+"进入初始化");
            int height = new Random().nextInt(100)+500;
            status.put(post.getObjectId(),height);
        }
        ViewGroup.LayoutParams layoutParams = holder.postsContent.getLayoutParams();
        layoutParams.height =status.get(post.getObjectId()) ;
        holder.postsContent.setLayoutParams(layoutParams);

        //设置内容
        holder.postTitle.setText(post.getTitle());
        //TODO: 切换为名字
        holder.postAuther.setText("无敌大魔王喵");
        holder.postDate.setText(post.getDate().getDate());
        holder.postContent.setText(post.getContent());




        //TODO: 按钮事件
        //绑定ClickListener
        holder.postContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 为什么holder.getLayoutPosition()
                mOnDetailClickListener.onDetailClick(view,holder.getLayoutPosition(),like.get(post.getObjectId()));
            }
        });

        holder.postCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCommentClickListener.onCommentClick(view,holder.getLayoutPosition());
            }
        });

        holder.postShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnShareClickListener.onShareClick(view,holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void changeLike(List<Post> likes) {
        for (Post post :likes){
            like.putIfAbsent(post.getObjectId(),1);
            like.replace(post.getObjectId(),1);
            Log.e("this","添加"+post.getTitle()+"成功，hashmap共有"+like.size()+ like.get(post));
        }
        notifyDataSetChanged();
    }
    public void changeLike(Post post){
        if(like.get(post.getObjectId())==0){
            like.replace(post.getObjectId(),1);
        }else
            like.replace(post.getObjectId(),0);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View postsContent;
        public TextView postTitle,postAuther,postDate,postContent;
        public Button postDetailButton,postLikeButton,postCommentButton,postShareButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postsContent = itemView.findViewById(R.id.postscontent);
            postTitle = itemView.findViewById(R.id.posttitle);
            postAuther = itemView.findViewById(R.id.postauther);
            postDate = itemView.findViewById(R.id.postdate);
            postContent = itemView.findViewById(R.id.postcontent);
            postDetailButton = itemView.findViewById(R.id.postbutton);
            postLikeButton = itemView.findViewById(R.id.postlike);
            postCommentButton = itemView.findViewById(R.id.postcomment);
            postShareButton = itemView.findViewById(R.id.postshare);

        }
    }



    //定义点赞接口 OnLikeClickListener
    public interface OnLikeClickListener {
        void onLikeClick(View view, int position,int type);
    }
    private OnLikeClickListener mOnLikeClickListener;

    public void setOnLikeClickListener(OnLikeClickListener onLikeClickListener) {
        mOnLikeClickListener = onLikeClickListener;
    }

    //定义评论接口 OnCommentClickListener
    public interface OnCommentClickListener {
        void onCommentClick(View view, int position);
    }
    private OnCommentClickListener mOnCommentClickListener;

    public void setOnCommentClickListener(OnCommentClickListener onCommentClickListener) {
        mOnCommentClickListener = onCommentClickListener;
    }

    //定义分享接口 OnShareClickListener
    public interface OnShareClickListener {
        void onShareClick(View view, int position);
    }
    private OnShareClickListener mOnShareClickListener;

    public void setOnShareClickListener(OnShareClickListener onShareClickListener) {
        mOnShareClickListener = onShareClickListener;
    }

    //定义详细信息接口 OnDetailClickListener
    public interface OnDetailClickListener {
        void onDetailClick(View view, int position,int like);
    }
    private OnDetailClickListener mOnDetailClickListener;

    public void setOnDetailClickListener(OnDetailClickListener onDetailClickListener) {
        mOnDetailClickListener = onDetailClickListener;
    }



    public void changelist(List<Post> list){
        posts = list;
        notifyDataSetChanged();
    }



}
