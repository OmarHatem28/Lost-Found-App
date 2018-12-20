package com.mm.warhit.lostfound.Model;

import java.util.ArrayList;

public class User {

    String name, email, password, phone;
    ArrayList<Post> posts;

    public User(String name, String email, String password, String phone, ArrayList<Post> posts) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.posts = posts;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
