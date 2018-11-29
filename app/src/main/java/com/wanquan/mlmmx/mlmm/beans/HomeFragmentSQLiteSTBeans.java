package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/24.
 */

public class HomeFragmentSQLiteSTBeans implements Serializable {
    private int id;
    private int week;
    private String sex;
    private Double min_height;
    private Double max_height;
    private Double min_weight;
    private Double max_weight;

    @Override
    public String toString() {
        return "HomeFragmentSQLiteSTBeans{" +
                "id=" + id +
                ", week=" + week +
                ", sex='" + sex + '\'' +
                ", min_height=" + min_height +
                ", max_height=" + max_height +
                ", min_weight=" + min_weight +
                ", max_weight=" + max_weight +
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getMin_height() {
        return min_height;
    }

    public void setMin_height(Double min_height) {
        this.min_height = min_height;
    }

    public Double getMax_height() {
        return max_height;
    }

    public void setMax_height(Double max_height) {
        this.max_height = max_height;
    }

    public Double getMin_weight() {
        return min_weight;
    }

    public void setMin_weight(Double min_weight) {
        this.min_weight = min_weight;
    }

    public Double getMax_weight() {
        return max_weight;
    }

    public void setMax_weight(Double max_weight) {
        this.max_weight = max_weight;
    }

    public HomeFragmentSQLiteSTBeans() {
    }

    public HomeFragmentSQLiteSTBeans(int id, int week, String sex, Double min_height, Double max_height, Double min_weight, Double max_weight) {
        this.id = id;
        this.week = week;
        this.sex = sex;
        this.min_height = min_height;
        this.max_height = max_height;
        this.min_weight = min_weight;
        this.max_weight = max_weight;
    }
}
