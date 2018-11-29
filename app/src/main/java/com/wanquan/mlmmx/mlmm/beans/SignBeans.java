package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SignBeans implements Serializable {
   /**
     * data : {"balanceAfterSign":40,"continuousDays":1,"levelAfterSign":1,"todayIntegral":5}
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
         * balanceAfterSign : 40
         * continuousDays : 1
         * levelAfterSign : 1
         * todayIntegral : 5
         */

        private int balanceAfterSign;
        private int continuousDays;
        private int levelAfterSign;
        private int todayIntegral;

        public int getBalanceAfterSign() {
            return balanceAfterSign;
        }

        public void setBalanceAfterSign(int balanceAfterSign) {
            this.balanceAfterSign = balanceAfterSign;
        }

        public int getContinuousDays() {
            return continuousDays;
        }

        public void setContinuousDays(int continuousDays) {
            this.continuousDays = continuousDays;
        }

        public int getLevelAfterSign() {
            return levelAfterSign;
        }

        public void setLevelAfterSign(int levelAfterSign) {
            this.levelAfterSign = levelAfterSign;
        }

        public int getTodayIntegral() {
            return todayIntegral;
        }

        public void setTodayIntegral(int todayIntegral) {
            this.todayIntegral = todayIntegral;
        }
    }
}
