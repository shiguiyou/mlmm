package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class PlayHistoryBeans implements Serializable {
    /**
     * data : [{"createTime":"2018-01-18 09:52:19","id":368,"userId":1,"voiceId":"66660458","voiceTimes":0,"voiceTitle":"《大闹天宫》主题曲\u2014\u2014吾乃齐天大圣孙悟空"},{"createTime":"2018-01-18 09:52:06","id":367,"userId":1,"voiceId":"62360883","voiceTimes":1200,"voiceTitle":"巴啦啦小魔仙之飞越彩灵堡-mix-01"},{"createTime":"2018-01-17 16:49:22","id":339,"userId":1,"voiceId":"67193698","voiceTimes":333,"voiceTitle":"61. 神奇的勇敢石"},{"createTime":"2018-01-17 14:49:34","id":291,"userId":1,"voiceId":"66407528","voiceTimes":0,"voiceTitle":"黑猫警长05.吃红土的小偷（上）"},{"createTime":"2018-01-15 16:01:22","id":16,"userId":1,"voiceId":"1","voiceTimes":1,"voiceTitle":"1111"}]
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
         * createTime : 2018-01-18 09:52:19
         * id : 368
         * userId : 1
         * voiceId : 66660458
         * voiceTimes : 0
         * voiceTitle : 《大闹天宫》主题曲——吾乃齐天大圣孙悟空
         */

        private String createTime;
        private int id;
        private int userId;
        private String voiceId;
        private int voiceTimes;
        private String voiceTitle;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVoiceId() {
            return voiceId;
        }

        public void setVoiceId(String voiceId) {
            this.voiceId = voiceId;
        }

        public int getVoiceTimes() {
            return voiceTimes;
        }

        public void setVoiceTimes(int voiceTimes) {
            this.voiceTimes = voiceTimes;
        }

        public String getVoiceTitle() {
            return voiceTitle;
        }

        public void setVoiceTitle(String voiceTitle) {
            this.voiceTitle = voiceTitle;
        }
    }
}
