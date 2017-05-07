package com.example.jiji.coursedesign.db;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/6.
 */

public class TextRecord extends DataSupport {
    private String content;
    private String time;
    private int id;
    private String alertTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
