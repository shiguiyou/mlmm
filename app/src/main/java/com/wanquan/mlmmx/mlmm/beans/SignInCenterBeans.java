package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SignInCenterBeans implements Serializable{
    /**
     * data : {"continueSignDays":0,"signHis":[{"gainIntegral":0,"signDate":"2018-04-04"},{"gainIntegral":0,"signDate":"2018-04-05"},{"gainIntegral":0,"signDate":"2018-04-06"},{"gainIntegral":0,"signDate":"2018-04-07"},{"gainIntegral":0,"signDate":"2018-04-08"},{"gainIntegral":0,"signDate":"2018-04-09"},{"gainIntegral":0,"signDate":"2018-04-10"},{"gainIntegral":0,"signDate":"2018-04-11"},{"gainIntegral":0,"signDate":"2018-04-12"},{"gainIntegral":0,"signDate":"2018-04-13"},{"gainIntegral":0,"signDate":"2018-04-14"},{"gainIntegral":0,"signDate":"2018-04-15"},{"gainIntegral":0,"signDate":"2018-04-16"},{"gainIntegral":0,"signDate":"2018-04-17"},{"gainIntegral":0,"signDate":"2018-04-18"}],"tomorrowSign":[{"gainIntegral":5,"signDate":"2018-04-19"},{"gainIntegral":5,"signDate":"2018-04-20"},{"gainIntegral":10,"signDate":"2018-04-21"},{"gainIntegral":10,"signDate":"2018-04-22"},{"gainIntegral":10,"signDate":"2018-04-23"},{"gainIntegral":10,"signDate":"2018-04-24"},{"gainIntegral":15,"signDate":"2018-04-25"},{"gainIntegral":15,"signDate":"2018-04-26"},{"gainIntegral":15,"signDate":"2018-04-27"},{"gainIntegral":15,"signDate":"2018-04-28"},{"gainIntegral":15,"signDate":"2018-04-29"},{"gainIntegral":15,"signDate":"2018-04-30"},{"gainIntegral":15,"signDate":"2018-05-01"},{"gainIntegral":15,"signDate":"2018-05-02"},{"gainIntegral":15,"signDate":"2018-05-03"}]}
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
         * continueSignDays : 0
         * signHis : [{"gainIntegral":0,"signDate":"2018-04-04"},{"gainIntegral":0,"signDate":"2018-04-05"},{"gainIntegral":0,"signDate":"2018-04-06"},{"gainIntegral":0,"signDate":"2018-04-07"},{"gainIntegral":0,"signDate":"2018-04-08"},{"gainIntegral":0,"signDate":"2018-04-09"},{"gainIntegral":0,"signDate":"2018-04-10"},{"gainIntegral":0,"signDate":"2018-04-11"},{"gainIntegral":0,"signDate":"2018-04-12"},{"gainIntegral":0,"signDate":"2018-04-13"},{"gainIntegral":0,"signDate":"2018-04-14"},{"gainIntegral":0,"signDate":"2018-04-15"},{"gainIntegral":0,"signDate":"2018-04-16"},{"gainIntegral":0,"signDate":"2018-04-17"},{"gainIntegral":0,"signDate":"2018-04-18"}]
         * tomorrowSign : [{"gainIntegral":5,"signDate":"2018-04-19"},{"gainIntegral":5,"signDate":"2018-04-20"},{"gainIntegral":10,"signDate":"2018-04-21"},{"gainIntegral":10,"signDate":"2018-04-22"},{"gainIntegral":10,"signDate":"2018-04-23"},{"gainIntegral":10,"signDate":"2018-04-24"},{"gainIntegral":15,"signDate":"2018-04-25"},{"gainIntegral":15,"signDate":"2018-04-26"},{"gainIntegral":15,"signDate":"2018-04-27"},{"gainIntegral":15,"signDate":"2018-04-28"},{"gainIntegral":15,"signDate":"2018-04-29"},{"gainIntegral":15,"signDate":"2018-04-30"},{"gainIntegral":15,"signDate":"2018-05-01"},{"gainIntegral":15,"signDate":"2018-05-02"},{"gainIntegral":15,"signDate":"2018-05-03"}]
         */

        private int continueSignDays;
        private List<TomorrowSignBean> signHis;
        private List<TomorrowSignBean> tomorrowSign;

        public int getContinueSignDays() {
            return continueSignDays;
        }

        public void setContinueSignDays(int continueSignDays) {
            this.continueSignDays = continueSignDays;
        }

        public List<TomorrowSignBean> getSignHis() {
            return signHis;
        }

        public void setSignHis(List<TomorrowSignBean> signHis) {
            this.signHis = signHis;
        }

        public List<TomorrowSignBean> getTomorrowSign() {
            return tomorrowSign;
        }

        public void setTomorrowSign(List<TomorrowSignBean> tomorrowSign) {
            this.tomorrowSign = tomorrowSign;
        }

        public static class SignHisBean {
            /**
             * gainIntegral : 0
             * signDate : 2018-04-04
             */

            private int gainIntegral;
            private String signDate;

            public int getGainIntegral() {
                return gainIntegral;
            }

            public void setGainIntegral(int gainIntegral) {
                this.gainIntegral = gainIntegral;
            }

            public String getSignDate() {
                return signDate;
            }

            public void setSignDate(String signDate) {
                this.signDate = signDate;
            }
        }

        public static class TomorrowSignBean {
            /**
             * gainIntegral : 5
             * signDate : 2018-04-19
             */

            private int gainIntegral;
            private String signDate;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public int getGainIntegral() {
                return gainIntegral;
            }

            public void setGainIntegral(int gainIntegral) {
                this.gainIntegral = gainIntegral;
            }

            public String getSignDate() {
                return signDate;
            }

            public void setSignDate(String signDate) {
                this.signDate = signDate;
            }
        }
    }
}
