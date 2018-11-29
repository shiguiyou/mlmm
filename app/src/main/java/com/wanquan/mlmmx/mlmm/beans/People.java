package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

public class People implements Serializable{

    private String time;
    private String value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}