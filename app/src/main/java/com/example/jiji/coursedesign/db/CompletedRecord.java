package com.example.jiji.coursedesign.db;

import org.litepal.crud.DataSupport;

/**
 * Created by jiji on 2017/5/26.
 */

public class CompletedRecord extends DataSupport {
    private String content;
    private String time;
    private String Title;
    private int id;
    private String alertTime;
    private int type;

    /**
     * type:
     * 0:默认分类，深灰色
     * 1:日常:蓝色
     * 2:工作,橙色
     * 3:经济,绿色
     * 4:重要,红色
     */

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
