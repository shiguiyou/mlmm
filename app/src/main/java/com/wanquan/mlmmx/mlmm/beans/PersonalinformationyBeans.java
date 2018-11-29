package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/3.
 */

public class PersonalinformationyBeans implements Serializable {
    /**
     * data : {"headIco":"/wq/imgs/100.png","mobile":"13800000000","myDeviceCount":1,"nickName":"qzg","shareDeviceCount":2}
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
         * headIco : /wq/imgs/100.png
         * mobile : 13800000000
         * myDeviceCount : 1
         * nickName : qzg
         * shareDeviceCount : 2
         */

        private String headIco;
        private String mobile;
        private int myDeviceCount;
        private String nickName;
        private int shareDeviceCount;

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getMyDeviceCount() {
            return myDeviceCount;
        }

        public void setMyDeviceCount(int myDeviceCount) {
            this.myDeviceCount = myDeviceCount;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getShareDeviceCount() {
            return shareDeviceCount;
        }

        public void setShareDeviceCount(int shareDeviceCount) {
            this.shareDeviceCount = shareDeviceCount;
        }
    }
}
