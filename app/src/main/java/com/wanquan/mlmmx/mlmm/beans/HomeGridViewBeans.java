package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/16.
 */

public class HomeGridViewBeans implements Serializable {
    private int imageView;
    private String text;

    public HomeGridViewBeans(int imageView, String text) {
        this.imageView = imageView;
        this.text = text;
    }

    public HomeGridViewBeans() {
    }

    @Override
    public String toString() {
        return "HomeGridViewBeans{" +
                "imageView='" + imageView + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
