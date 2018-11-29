package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23.
 */

public class BabyRoomShowBeans implements Serializable {
    /**
     * data : [{"time":15,"value":178},{"time":16,"value":49},{"time":17,"value":74},{"time":18,"value":32},{"time":19,"value":31},{"time":20,"value":35},{"time":21,"value":79},{"time":22,"value":30},{"time":23,"value":32},{"time":0,"value":33},{"time":1,"value":29},{"time":2,"value":32},{"time":3,"value":34},{"time":4,"value":34},{"time":5,"value":88},{"time":6,"value":312},{"time":7,"value":372},{"time":8,"value":110},{"time":9,"value":40},{"time":10,"value":119},{"time":11,"value":144},{"time":13,"value":136},{"time":14,"value":45},{"time":15,"value":83}]
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
         * time : 15
         * value : 178
         */

        private String time;
        private String value;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
