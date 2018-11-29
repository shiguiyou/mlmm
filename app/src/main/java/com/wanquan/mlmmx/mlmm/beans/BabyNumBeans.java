package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/20.
 */

public class BabyNumBeans implements Serializable {
    /**
     * data : {"deviceId":2,"humi":"86.0","ozone":"0.18","pm03":"302","pm25":"33","temp":"25.0"}
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
         * deviceId : 2
         * humi : 86.0
         * ozone : 0.18
         * pm03 : 302
         * pm25 : 33
         * temp : 25.0
         */

        private int deviceId;
        private String humi;
        private String ozone;
        private String pm03;
        private String pm25;
        private String temp;

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getHumi() {
            return humi;
        }

        public void setHumi(String humi) {
            this.humi = humi;
        }

        public String getOzone() {
            return ozone;
        }

        public void setOzone(String ozone) {
            this.ozone = ozone;
        }

        public String getPm03() {
            return pm03;
        }

        public void setPm03(String pm03) {
            this.pm03 = pm03;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }
    }
}
