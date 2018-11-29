package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class PlayCollectionBeans implements Serializable {
    /**
     * data : [{"album_id":"12052283","album_img":null,"album_title":null,"create_time":"2018-01-16 15:55:58","id":59,"user_id":205,"voice_count":11},{"album_id":"12490447","album_img":null,"album_title":null,"create_time":"2018-01-16 15:55:47","id":58,"user_id":205,"voice_count":11},{"album_id":"1","album_img":"http://localhost:58080/architecture-iot-business/albumUrl/123.png","album_title":"我喜欢","create_time":"2018-01-16 15:26:44","id":54,"user_id":205,"voice_count":2}]
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;
    private List<DataBean> data;

    public String getDynamicKey() {
        return dynamicKey;
    }

    public void setDynamicKey(String dynamicKey) {
        this.dynamicKey = dynamicKey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * album_id : 12052283
         * album_img : null
         * album_title : null
         * create_time : 2018-01-16 15:55:58
         * id : 59
         * user_id : 205
         * voice_count : 11
         */

        private String album_id;
        private String album_img;
        private String album_title;
        private String create_time;
        private int id;
        private int user_id;
        private int voice_count;

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_img() {
            return album_img;
        }

        public void setAlbum_img(String album_img) {
            this.album_img = album_img;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getVoice_count() {
            return voice_count;
        }

        public void setVoice_count(int voice_count) {
            this.voice_count = voice_count;
        }
    }
}
