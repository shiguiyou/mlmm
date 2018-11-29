package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/15.
 */

public class FetalMovementQueryBeans implements Serializable {
    /**
     * data : [{"clickTimes":0,"createDate":"2018-05-15","currentTime":"2018-05-15 15:10:56","endTime":"2018-05-15 16:10:37","lastClickTime":null,"mid":39,"moveTimes":0,"startTime":"2018-05-15 15:10:37","status":0,"userId":311}]
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
         * clickTimes : 0
         * createDate : 2018-05-15
         * currentTime : 2018-05-15 15:10:56
         * endTime : 2018-05-15 16:10:37
         * lastClickTime : null
         * mid : 39
         * moveTimes : 0
         * startTime : 2018-05-15 15:10:37
         * status : 0
         * userId : 311
         */

        private String clickTimes;
        private String createDate;
        private String currentTime;
        private String endTime;
        private Object lastClickTime;
        private int mid;
        private String moveTimes;
        private String startTime;
        private int status;
        private int userId;

        public String getClickTimes() {
            return clickTimes;
        }

        public void setClickTimes(String clickTimes) {
            this.clickTimes = clickTimes;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(String currentTime) {
            this.currentTime = currentTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Object getLastClickTime() {
            return lastClickTime;
        }

        public void setLastClickTime(Object lastClickTime) {
            this.lastClickTime = lastClickTime;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getMoveTimes() {
            return moveTimes;
        }

        public void setMoveTimes(String moveTimes) {
            this.moveTimes = moveTimes;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
