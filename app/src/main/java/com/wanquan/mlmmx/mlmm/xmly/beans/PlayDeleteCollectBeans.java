package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class PlayDeleteCollectBeans implements Serializable {

    /**
     * data : [{"album_id":1,"create_time":"2018-01-16 15:01:26","id":57,"user_id":1,"voice_id":"66407528","voice_times":295,"voice_title":"《邋遢大王》主题曲\u2014\u2014不爱干净会被老鼠看上哦","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"}]
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
         * album_id : 1
         * create_time : 2018-01-16 15:01:26
         * id : 57
         * user_id : 1
         * voice_id : 66407528
         * voice_times : 295
         * voice_title : 《邋遢大王》主题曲——不爱干净会被老鼠看上哦
         * voice_url : http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3
         */

        private int album_id;
        private String create_time;
        private int id;
        private int user_id;
        private String voice_id;
        private int voice_times;
        private String voice_title;
        private String voice_url;

        public int getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(int album_id) {
            this.album_id = album_id;
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

        public String getVoice_id() {
            return voice_id;
        }

        public void setVoice_id(String voice_id) {
            this.voice_id = voice_id;
        }

        public int getVoice_times() {
            return voice_times;
        }

        public void setVoice_times(int voice_times) {
            this.voice_times = voice_times;
        }

        public String getVoice_title() {
            return voice_title;
        }

        public void setVoice_title(String voice_title) {
            this.voice_title = voice_title;
        }

        public String getVoice_url() {
            return voice_url;
        }

        public void setVoice_url(String voice_url) {
            this.voice_url = voice_url;
        }
    }
}
