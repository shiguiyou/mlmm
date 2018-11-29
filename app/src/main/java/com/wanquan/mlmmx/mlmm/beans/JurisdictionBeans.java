package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/1.
 */

public class JurisdictionBeans  implements Serializable{
    /**
     * data : 1
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private int data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public int getData() {
        return data;
    }

    public void setData(int data) {
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
