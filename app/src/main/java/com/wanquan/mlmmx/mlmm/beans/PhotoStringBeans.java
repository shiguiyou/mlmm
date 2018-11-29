package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/10.
 */

public class PhotoStringBeans implements Serializable {
    private String str;

    public PhotoStringBeans() {
    }

    @Override
    public String toString() {
        return "PhotoStringBeans{" +
                "str='" + str + '\'' +
                '}';
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public PhotoStringBeans(String str) {
        this.str = str;
    }
}
