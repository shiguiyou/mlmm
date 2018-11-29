package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class InviteRecordBeans implements Serializable {
    /**
     * data : [{"baby_id":613,"finish_time":null,"id":38,"mobile":"15312030631","relationship":"妈妈","request_time":"2018-03-01 21:45:48","status":2},{"baby_id":613,"finish_time":null,"id":37,"mobile":"15312030631","relationship":"妈妈","request_time":"2018-03-01 21:44:43","status":2},{"baby_id":613,"finish_time":null,"id":34,"mobile":"15261825221","relationship":"姑姑","request_time":"2018-03-01 16:52:10","status":-1},{"baby_id":613,"finish_time":null,"id":30,"mobile":"123123","relationship":"舅舅","request_time":"2018-02-28 17:16:05","status":-1},{"baby_id":613,"finish_time":null,"id":29,"mobile":"15261825221","relationship":"妈妈","request_time":"2018-02-28 17:08:14","status":-1}]
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
         * baby_id : 613
         * finish_time : null
         * id : 38
         * mobile : 15312030631
         * relationship : 妈妈
         * request_time : 2018-03-01 21:45:48
         * status : 2
         */

        private int baby_id;
        private Object finish_time;
        private int id;
        private String mobile;
        private String relationship;
        private String request_time;
        private int status;

        public int getBaby_id() {
            return baby_id;
        }

        public void setBaby_id(int baby_id) {
            this.baby_id = baby_id;
        }

        public Object getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(Object finish_time) {
            this.finish_time = finish_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public String getRequest_time() {
            return request_time;
        }

        public void setRequest_time(String request_time) {
            this.request_time = request_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
