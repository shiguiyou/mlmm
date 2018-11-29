package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by Administrator on 2017/9/28.
 */

public class EquipmentAddBeans {


    /**
     * data : {"callBackResult":true,"msg":"添加成功","object":null,"result":1,"validateResult":1}
     * dynamicKey :
     * msg : 添加成功
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
         * callBackResult : true
         * msg : 添加成功
         * object : null
         * result : 1
         * validateResult : 1
         */

        private boolean callBackResult;
        private String msg;
        private Object object;
        private int result;
        private int validateResult;

        public boolean isCallBackResult() {
            return callBackResult;
        }

        public void setCallBackResult(boolean callBackResult) {
            this.callBackResult = callBackResult;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getValidateResult() {
            return validateResult;
        }

        public void setValidateResult(int validateResult) {
            this.validateResult = validateResult;
        }
    }
}
