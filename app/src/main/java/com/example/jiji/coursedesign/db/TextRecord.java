package com.example.jiji.coursedesign.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jiji on 2017/5/6.
 */

public class TextRecord extends DataSupport implements Serializable {
    private String content;
    private String time;
    private String Title;
    private int id;
    private String alertTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }
}
