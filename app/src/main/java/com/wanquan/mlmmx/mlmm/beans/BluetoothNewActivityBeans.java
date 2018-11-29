package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/11.
 */

public class BluetoothNewActivityBeans implements Serializable {
    private String name;
    private String src;

    public BluetoothNewActivityBeans(String name, String src) {
        this.name = name;
        this.src = src;
    }

    public BluetoothNewActivityBeans() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
