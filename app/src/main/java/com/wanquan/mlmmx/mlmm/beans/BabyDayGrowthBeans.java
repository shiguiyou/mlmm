package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BabyDayGrowthBeans implements Serializable{
    private int id;
    private int day;
    private String description;
    private Double height;
    private Double weight;

    @Override
    public String toString() {
        return "BabyDayGrowthBeans{" +
                "id=" + id +
                ", day=" + day +
                ", description='" + description + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BabyDayGrowthBeans() {
    }

    public BabyDayGrowthBeans(int id, int day, String description, Double height, Double weight) {
        this.id = id;
        this.day = day;
        this.description = description;
        this.height = height;
        this.weight = weight;
    }
}
