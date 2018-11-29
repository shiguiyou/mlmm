package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MyLikeListActivityBeans implements Serializable {

    /**
     * data : [{"album_id":1,"create_time":"2018-01-26 21:40:41","id":164,"user_id":1,"voice_id":"60519756","voice_times":518,"voice_title":"1.在这九个海洋巨兽面前，人类如此渺小","voice_url":"http://fdfs.xmcdn.com/group32/M0B/1D/F0/wKgJUFof93aSkekaAD9HeIQqX3Q987.mp3"},{"album_id":1,"create_time":"2018-01-22 09:22:04","id":131,"user_id":1,"voice_id":null,"voice_times":46,"voice_title":"《邋遢大王》主题曲\u2014\u2014不爱干净会被老鼠看上哦","voice_url":"http://fdfs.xmcdn.com/group34/M09/50/E3/wKgJYFpYIibDSXGWAAW0Fth4Ohg191.mp3"},{"album_id":1,"create_time":"2018-01-20 15:49:57","id":129,"user_id":1,"voice_id":null,"voice_times":null,"voice_title":"6.世上最浪漫的事，就是我到人类去不了的地方给爸爸找一个四百年的花瓶","voice_url":"http://fdfs.xmcdn.com/group33/M0A/29/7C/wKgJUVomTBzhO8RbABt9BXdH-eo416.mp3"},{"album_id":1,"create_time":"2018-01-17 09:34:36","id":90,"user_id":1,"voice_id":"66108626","voice_times":646,"voice_title":"涂涂老师：越王勾践剑\u2014\u2014卧薪尝胆背后的故事","voice_url":"http://fdfs.xmcdn.com/group37/M08/1D/03/wKgJoVpS-hjjpGbDAE7lurrPRfw632.mp3"},{"album_id":1,"create_time":"2018-01-16 15:09:57","id":65,"user_id":1,"voice_id":"66660458","voice_times":295,"voice_title":"起点：致童年\u2014\u2014国产动画串烧！","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"},{"album_id":1,"create_time":"2018-01-16 15:09:22","id":62,"user_id":1,"voice_id":"66660458","voice_times":295,"voice_title":"起点：致童年\u2014\u2014国产动画串烧！","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"},{"album_id":1,"create_time":"2018-01-16 15:09:22","id":63,"user_id":1,"voice_id":"66660458","voice_times":295,"voice_title":"起点：致童年\u2014\u2014国产动画串烧！","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"},{"album_id":1,"create_time":"2018-01-16 15:09:13","id":61,"user_id":1,"voice_id":"66660458","voice_times":295,"voice_title":"起点：致童年\u2014\u2014国产动画串烧！","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"},{"album_id":1,"create_time":"2018-01-16 15:09:11","id":60,"user_id":1,"voice_id":"66660458","voice_times":295,"voice_title":"起点：致童年\u2014\u2014国产动画串烧！","voice_url":"http://fdfs.xmcdn.com/group30/M04/4D/66/wKgJWlpYIZPS685EACQLGeGugD4032.mp3"},{"album_id":1,"create_time":"2018-01-15 08:46:58","id":5,"user_id":1,"voice_id":"1","voice_times":1,"voice_title":"1","voice_url":"1"},{"album_id":1,"create_time":"2018-01-15 08:45:28","id":4,"user_id":1,"voice_id":"1","voice_times":1,"voice_title":"1","voice_url":"1"},{"album_id":1,"create_time":"2018-01-12 15:20:45","id":3,"user_id":1,"voice_id":"2","voice_times":1,"voice_title":"1","voice_url":"1"},{"album_id":1,"create_time":"2018-01-12 15:19:24","id":2,"user_id":1,"voice_id":null,"voice_times":null,"voice_title":null,"voice_url":null}]
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
         * create_time : 2018-01-26 21:40:41
         * id : 164
         * user_id : 1
         * voice_id : 60519756
         * voice_times : 518
         * voice_title : 1.在这九个海洋巨兽面前，人类如此渺小
         * voice_url : http://fdfs.xmcdn.com/group32/M0B/1D/F0/wKgJUFof93aSkekaAD9HeIQqX3Q987.mp3
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
