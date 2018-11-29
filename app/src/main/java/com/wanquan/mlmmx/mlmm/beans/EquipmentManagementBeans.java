package com.wanquan.mlmmx.mlmm.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class EquipmentManagementBeans {


    /**
     * data : [{"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","mobile":"18951603156","nickName":"空气","shareUserId":5},{"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","mobile":"15850530383","nickName":"***","shareUserId":7},{"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","mobile":"15850515615","nickName":"***","shareUserId":8}]
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
         * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
         * mobile : 18951603156
         * nickName : 空气
         * shareUserId : 5
         */

        private String headIco;
        private String mobile;
        private String nickName;
        private int shareUserId;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getShareUserId() {
            return shareUserId;
        }

        public void setShareUserId(int shareUserId) {
            this.shareUserId = shareUserId;
        }
    }
}
