package com.mm.warhit.lostfound.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mm.warhit.lostfound.Model.Post;
import com.mm.warhit.lostfound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostHolder>{

    ArrayList<Post> posts;
    Context context;

    public PostsAdapter(ArrayList<Post> posts, Context context){
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.post,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        Post post = posts.get(position);

        //Todo: Get image from post.getItemImage()
        Picasso.get().load(R.drawable.lostandfound).into(holder.itemImage);
        holder.author.setText("Posted By: "+post.getAuthor());
        holder.category.setText("Category: "+post.getCategory());
        holder.description.setText("Description: "+post.getDescription());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        ImageView itemImage;
        TextView author, description, category;

        public PostHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.itemImage);
            author = itemView.findViewById(R.id.author);
            description = itemView.findViewById(R.id.description);
            category = itemView.findViewById(R.id.category);

        }
    }

}
