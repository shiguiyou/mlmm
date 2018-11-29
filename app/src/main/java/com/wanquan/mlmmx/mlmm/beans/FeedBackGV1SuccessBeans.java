package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/12.
 */

public class FeedBackGV1SuccessBeans implements Serializable {
    /**
     * data : {"extend1":"","feedBackId":243,"getIntegral":0,"returnCode":1,"returnMsg":""}
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
         * extend1 :
         * feedBackId : 243
         * getIntegral : 0
         * returnCode : 1
         * returnMsg :
         */

        private String extend1;
        private int feedBackId;
        private int getIntegral;
        private int returnCode;
        private String returnMsg;

        public String getExtend1() {
            return extend1;
        }

        public void setExtend1(String extend1) {
            this.extend1 = extend1;
        }

        public int getFeedBackId() {
            return feedBackId;
        }

        public void setFeedBackId(int feedBackId) {
            this.feedBackId = feedBackId;
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
