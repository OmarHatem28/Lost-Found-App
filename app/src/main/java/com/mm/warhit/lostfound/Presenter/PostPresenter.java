package com.mm.warhit.lostfound.Presenter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mm.warhit.lostfound.Model.Post;
import com.mm.warhit.lostfound.View.PostView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PostPresenter {

    PostView postView;
    Context context;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference postsRef;
    ArrayList<Post> posts = new ArrayList<>();

    public PostPresenter(Context context, PostView postView){
        this.context = context;
        this.postView = postView;
    }

    public void getPosts(){
        postsRef = db.collection("posts");
        postsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            Map<String, Object> map;
                            for (int i = 0; i < querySnapshot.size(); i++) {
                                map = querySnapshot.getDocuments().get(i).getData();
                                Post post = new Post();
                                post.setSerialNumber(map.get("serialNumber").toString());
                                post.setDescription(map.get("description").toString());
                                post.setCategory(map.get("category").toString());
                                post.setAuthor(map.get("author").toString());
//                                post.setItemImage((Uri)map.get("itemImage"));

                                posts.add(post);
                            }
                            Collections.reverse(posts);
                            postView.getAllPosts(posts);
                        } else {
                            postView.onRetrieveFailure();
                        }
                    }
                });
    }

    public void addPost(final Post post) {

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        Map<String, Object> mypost;
        mypost = new HashMap<>();
        mypost.put("author", post.getAuthor());
        mypost.put("description", post.getDescription());
        mypost.put("serialNumber",post.getSerialNumber());
        mypost.put("category","not now");
        mypost.put("itemImage","not now");

        db.collection("posts").document(formattedDate)
                .set(mypost)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        posts.add(0,post);
                        postView.getAllPosts(posts);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postView.onAddingFailure();
                    }
                });
    }
}
