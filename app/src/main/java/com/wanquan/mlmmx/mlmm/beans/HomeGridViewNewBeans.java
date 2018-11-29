package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class HomeGridViewNewBeans implements Serializable {

    /**
     * data : [{"id":1,"name":"宝宝相册","optional":0,"orderNum":1,"status":"0;1;2","type":0,"userId":null},{"id":2,"name":"情感日志","optional":0,"orderNum":2,"status":"0;1;2","type":0,"userId":null},{"id":3,"name":"发育变化","optional":0,"orderNum":3,"status":"1,2","type":0,"userId":null},{"id":4,"name":"宝宝听听","optional":0,"orderNum":4,"status":"0;1;2","type":1,"userId":null},{"id":10,"name":"亲子任务","optional":1,"orderNum":10,"status":"2","type":1,"userId":null},{"id":11,"name":"成长曲线","optional":1,"orderNum":11,"status":"2","type":1,"userId":null},{"id":12,"name":"疫苗提醒","optional":1,"orderNum":12,"status":"2","type":1,"userId":null},{"id":13,"name":"月子餐","optional":1,"orderNum":13,"status":"2","type":1,"userId":null}]
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
         * id : 1
         * name : 宝宝相册
         * optional : 0
         * orderNum : 1
         * status : 0;1;2
         * type : 0
         * userId : null
         */

        private String id;
        private String name;
        private String optional;
        private String orderNum;
        private String status;
        private String type;
        private Object userId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOptional() {
            return optional;
        }

        public void setOptional(String optional) {
            this.optional = optional;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }
    }
}
