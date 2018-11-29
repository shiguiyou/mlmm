package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/26.
 */

public class BabyDevelopmentListViewBeans implements Serializable{
    private int id;
    private int week;
    private String title;
    private String content;

    @Override
    public String toString() {
        return "BabyDevelopmentListViewBeans{" +
                "id=" + id +
                ", week=" + week +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public BabyDevelopmentListViewBeans() {
    }

    public BabyDevelopmentListViewBeans(int id, int week, String title, String content) {
        this.id = id;
        this.week = week;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

