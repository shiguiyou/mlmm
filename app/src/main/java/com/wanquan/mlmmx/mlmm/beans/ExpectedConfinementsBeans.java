package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by XCF on 2018/10/16.
 */

public class ExpectedConfinementsBeans implements Serializable {
    /**
     * data : {"babyId":1012,"extend1":"","getIntegral":10,"returnCode":0,"returnMsg":""}
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private BabyMessageAddBeans.DataBean data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public BabyMessageAddBeans.DataBean getData() {
        return data;
    }

    public void setData(BabyMessageAddBeans.DataBean data) {
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
         * babyId : 1012
         * extend1 :
         * getIntegral : 10
         * returnCode : 0
         * returnMsg :
         */

        private int babyId;
        private String extend1;
        private int getIntegral;
        private int returnCode;
        private String returnMsg;

        public int getBabyId() {
            return babyId;
        }

        public void setBabyId(int babyId) {
            this.babyId = babyId;
        }

        public String getExtend1() {
            return extend1;
        }

        public void setExtend1(String extend1) {
            this.extend1 = extend1;
        }

        public int getGetIntegral() {
            return getIntegral;
        }

        public void setGetIntegral(int getIntegral) {
            this.getIntegral = getIntegral;
        }

        public int getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(int returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMsg() {
            return returnMsg;
        }

        public void setReturnMsg(String returnMsg) {
            this.returnMsg = returnMsg;
        }
    }
}
