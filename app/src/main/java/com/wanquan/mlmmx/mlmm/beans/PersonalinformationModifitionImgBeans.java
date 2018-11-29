package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by Administrator on 2017/9/21.
 */

public class PersonalinformationModifitionImgBeans {
    /**
     * data : http://192.168.10.32:58080/architecture-iot-business/img/head/1.png
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private String data;
    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
