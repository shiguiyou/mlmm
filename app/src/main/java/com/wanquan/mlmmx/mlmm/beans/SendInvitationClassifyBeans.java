package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SendInvitationClassifyBeans implements Serializable {
    /**
     * data : [{"id":6,"key":"ask.type","seq":0,"value":"孕育知识"},{"id":7,"key":"ask.type","seq":1,"value":"食物"},{"id":8,"key":"ask.type","seq":2,"value":"坐月子"}]
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
         * id : 6
         * key : ask.type
         * seq : 0
         * value : 孕育知识
         */

        private int id;
        private String key;
        private int seq;
        private String value;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
