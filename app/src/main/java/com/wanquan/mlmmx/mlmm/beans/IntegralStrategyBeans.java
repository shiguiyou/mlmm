package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class IntegralStrategyBeans implements Serializable{

    /**
     * data : [{"strategyName":"添加宝宝","strategyVal":10},{"strategyName":"推荐用户注册","strategyVal":50},{"strategyName":"发帖或回帖","strategyVal":10},{"strategyName":"绑定设备","strategyVal":10000},{"strategyName":"数据分享","strategyVal":10},{"strategyName":"签到","strategyVal":5},{"strategyName":"邀请亲友","strategyVal":10},{"strategyName":"亲子任务","strategyVal":5},{"strategyName":"分享","strategyVal":10}]
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
         * strategyName : 添加宝宝
         * strategyVal : 10
         */

        private String strategyName;
        private int strategyVal;

        public String getStrategyName() {
            return strategyName;
        }

        public void setStrategyName(String strategyName) {
            this.strategyName = strategyName;
        }

        public int getStrategyVal() {
            return strategyVal;
        }

        public void setStrategyVal(int strategyVal) {
            this.strategyVal = strategyVal;
        }
    }
}
