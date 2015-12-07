package com.cx.android.photogallery.model;

/**
 * Created by 陈雪 on 2015/10/8.
 */
public class GalleryItem {
    private String id;
    private String imageUrl;
    private String title;

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
