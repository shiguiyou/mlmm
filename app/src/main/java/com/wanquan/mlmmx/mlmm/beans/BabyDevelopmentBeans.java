package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/23.
 */

public class BabyDevelopmentBeans implements Serializable {
    private int week;
    private boolean isCheck;

    public BabyDevelopmentBeans() {
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public BabyDevelopmentBeans(int week, boolean isCheck) {
        this.week = week;
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "BabyDevelopmentBeans{" +
                "week=" + week +
                ", isCheck=" + isCheck +
                '}';
    }
}
