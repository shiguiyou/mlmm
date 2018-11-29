package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/17.
 */

public class ParentTaskBeans2 implements Serializable {

    /**
     * WEEK : 0
     * done : 1
     * id : 1332
     * title : 了解新生儿最初几天的外观和行为变化
     * url : http://stlib.qbb6.com/cnt0/article/0/8909w4zas5/1514970066663.html
     */

    private int WEEK;
    private int done;
    private int id;
    private String title;
    private String url;

    public int getWEEK() {
        return WEEK;
    }

    public void setWEEK(int WEEK) {
        this.WEEK = WEEK;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
