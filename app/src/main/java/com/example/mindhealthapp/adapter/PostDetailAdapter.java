package com.example.mindhealthapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mindhealthapp.Bean.Comment;
import com.example.mindhealthapp.Bean.Post;
import com.example.mindhealthapp.R;

import java.util.List;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder>{
    private List<Comment> comments ;
    private Post post;
    public PostDetailAdapter(List<Comment> comments, Post post){
        this.comments = comments;
        this.post = post;
    }

    @NonNull
    @Override
    public PostDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_content,parent,false);
        return new PostDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostDetailAdapter.ViewHolder holder, int position) {
        String author,date,content;
        if(position==0){
            author = post.getSender().getName();
            date = post.getDate().getDate();
            content = post.getContent();
        }else {
            Comment comment = comments.get(position-1);
            author = comment.getNickname();
            date = comment.getDate().getDate();
            content = comment.getContent();
        }
        holder.commentAuther.setText(author);
        holder.commentDate.setText(date);
        holder.commentContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return comments.size()+1;
    }

    public void changeList(List<Comment> list) {
        comments = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView commentAuther,commentDate,commentContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentAuther = itemView.findViewById(R.id.commentauther);
            commentDate = itemView.findViewById(R.id.commentdate);
            commentContent = itemView.findViewById(R.id.commentcontent);
        }
    }

}
