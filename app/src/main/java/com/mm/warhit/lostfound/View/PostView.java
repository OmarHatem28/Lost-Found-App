package com.mm.warhit.lostfound.View;

import com.mm.warhit.lostfound.Model.Post;

import java.util.ArrayList;

public interface PostView {

    void getAllPosts(ArrayList<Post> posts);

//    void addMyPost();

    void onRetrieveFailure();

    void onAddingFailure();

}
