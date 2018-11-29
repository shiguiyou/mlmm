package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class QuickeningHistoryBeans implements Serializable {
    /**
     * data : [{"createDate":"2018-05-17","expect12":0,"fetalMoveHis":[{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:22","lastClickTime":null,"mid":80,"moveTimes":0,"startTime":"2018-05-17 10:48:22","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:25","lastClickTime":null,"mid":81,"moveTimes":0,"startTime":"2018-05-17 10:48:25","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:26","lastClickTime":null,"mid":82,"moveTimes":0,"startTime":"2018-05-17 10:48:26","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:36","lastClickTime":null,"mid":83,"moveTimes":0,"startTime":"2018-05-17 10:48:36","status":1,"userId":311}],"resultMsg":"异常"}]
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
         * createDate : 2018-05-17
         * expect12 : 0
         * fetalMoveHis : [{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:22","lastClickTime":null,"mid":80,"moveTimes":0,"startTime":"2018-05-17 10:48:22","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:25","lastClickTime":null,"mid":81,"moveTimes":0,"startTime":"2018-05-17 10:48:25","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:26","lastClickTime":null,"mid":82,"moveTimes":0,"startTime":"2018-05-17 10:48:26","status":1,"userId":311},{"clickTimes":0,"createDate":"2018-05-17","currentTime":"2018-05-17 10:49:02","endTime":"2018-05-17 11:48:36","lastClickTime":null,"mid":83,"moveTimes":0,"startTime":"2018-05-17 10:48:36","status":1,"userId":311}]
         * resultMsg : 异常
         */

        private String createDate;
        private String expect12;
        private String resultMsg;
        private List<FetalMoveHisBean> fetalMoveHis;

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getExpect12() {
            return expect12;
        }

        public void setExpect12(String expect12) {
            this.expect12 = expect12;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
        }

        public List<FetalMoveHisBean> getFetalMoveHis() {
            return fetalMoveHis;
        }

        public void setFetalMoveHis(List<FetalMoveHisBean> fetalMoveHis) {
            this.fetalMoveHis = fetalMoveHis;
        }

        public static class FetalMoveHisBean {
            /**
             * clickTimes : 0
             * createDate : 2018-05-17
             * currentTime : 2018-05-17 10:49:02
             * endTime : 2018-05-17 11:48:22
             * lastClickTime : null
             * mid : 80
             * moveTimes : 0
             * startTime : 2018-05-17 10:48:22
             * status : 1
             * userId : 311
             */

            private String clickTimes;
            private String createDate;
            private String currentTime;
            private String endTime;
            private Object lastClickTime;
            private String mid;
            private String moveTimes;
            private String startTime;
            private String status;
            private String userId;

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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
