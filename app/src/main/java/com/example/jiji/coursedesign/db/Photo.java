package com.example.jiji.coursedesign.db;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/4.
 */

public class Photo extends DataSupport {
    private String describe;
    private String imageUrl;
    private String time;

    public Photo(String describe, String imageUrl) {
        this.describe = describe;
        this.imageUrl = imageUrl;
    }

    public Photo() {
        
    }

    public Photo(String describe, String imageUrl, String time) {
        this.describe = describe;
        this.imageUrl = imageUrl;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
