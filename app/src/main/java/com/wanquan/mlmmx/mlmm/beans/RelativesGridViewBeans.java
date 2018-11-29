package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/30.
 */

public class RelativesGridViewBeans implements Serializable {
    private String name;

    public RelativesGridViewBeans() {
    }

    public RelativesGridViewBeans(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
