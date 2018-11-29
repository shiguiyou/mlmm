package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/15.
 */

public class FetalMovementOperationBeans implements Serializable{
    /**
     * data : {"babyBoveTimes":1,"clickTimes":1}
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private DataBean data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * babyBoveTimes : 1
         * clickTimes : 1
         */

        private String babyBoveTimes;
        private String clickTimes;

        public String getBabyBoveTimes() {
            return babyBoveTimes;
        }

        public void setBabyBoveTimes(String babyBoveTimes) {
            this.babyBoveTimes = babyBoveTimes;
        }

        public String getClickTimes() {
            return clickTimes;
        }

        public void setClickTimes(String clickTimes) {
            this.clickTimes = clickTimes;
        }
    }
}
