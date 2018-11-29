package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/15.
 */

public class FetalMovementStopBeans implements Serializable {
    /**
     * data : null
     * dynamicKey :
     * msg : 本次数胎动结束！
     * result : true
     * resultCode : 1
     * token :
     */

    private Object data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
}
