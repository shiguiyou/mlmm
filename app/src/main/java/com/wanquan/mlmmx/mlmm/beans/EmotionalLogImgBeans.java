package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/21.
 */

public class EmotionalLogImgBeans implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private List<String> albumImglist;

        public List<String> getAlbumImglist() {
            return albumImglist;
        }

        public void setAlbumImglist(List<String> albumImglist) {
            this.albumImglist = albumImglist;
        }
    }
}
