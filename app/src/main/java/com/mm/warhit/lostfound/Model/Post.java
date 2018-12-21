package com.mm.warhit.lostfound.Model;

import android.net.Uri;

public class Post {

    String description, category, serialNumber, author;
    Uri itemImage;

    public Post(String description, String category, String serialNumber, String author, Uri itemImage) {
        this.description = description;
        this.category = category;
        this.serialNumber = serialNumber;
        this.author = author;
        this.itemImage = itemImage;
    }

    public Post() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Uri getItemImage() {
        return itemImage;
    }

    public void setItemImage(Uri itemImage) {
        this.itemImage = itemImage;
    }
}
