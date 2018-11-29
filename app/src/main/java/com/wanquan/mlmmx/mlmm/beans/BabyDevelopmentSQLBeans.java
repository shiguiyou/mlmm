package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class BabyDevelopmentSQLBeans implements Serializable {
    private int id;
    private int week;
    private String key;
    private String dataTitle;
    private String dataIntro;

    @Override
    public String toString() {
        return "BabyDevelopmentSQLBeans{" +
                "id=" + id +
                ", week=" + week +
                ", key='" + key + '\'' +
                ", dataTitle='" + dataTitle + '\'' +
                ", dataIntro='" + dataIntro + '\'' +
                '}';
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataIntro() {
        return dataIntro;
    }

    public void setDataIntro(String dataIntro) {
        this.dataIntro = dataIntro;
    }

    public BabyDevelopmentSQLBeans() {
    }

    public BabyDevelopmentSQLBeans(int id, int week, String key, String dataTitle, String dataIntro) {
        this.id = id;
        this.week = week;
        this.key = key;
        this.dataTitle = dataTitle;
        this.dataIntro = dataIntro;
    }
}
