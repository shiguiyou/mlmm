package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class IntegralGainIntegralBeans implements Serializable {
    /**
     * data : [{"amount":5,"balance":15,"createTime":"2018-04-19","direction":"IN","reason":"SIGN","remark":null,"userId":311},{"amount":10,"balance":25,"createTime":"2018-04-23","direction":"IN","reason":"POSTFORUM","remark":null,"userId":311},{"amount":5,"balance":30,"createTime":"2018-04-23","direction":"IN","reason":"SIGN","remark":null,"userId":311},{"amount":5,"balance":35,"createTime":"2018-04-24","direction":"IN","reason":"SIGN","remark":null,"userId":311},{"amount":2800,"balance":5200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":2400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":1400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":3200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":97200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":94400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":91600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":88800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":86000,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":83200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":80400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":77600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":76600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":73800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":71000,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":68200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":65400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":62600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":59800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":57000,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":54200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":53200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":50400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":47600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":44800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":42000,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":39200,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":36400,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":33600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":32600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":31600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":30600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":29600,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":2800,"balance":26800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":25800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":24800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":23800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":22800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":21800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":20800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":19800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":18800,"createTime":"2018-04-24","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":10,"balance":18810,"createTime":"2018-04-25","direction":"IN","reason":"SIGN","remark":null,"userId":311},{"amount":2800,"balance":16010,"createTime":"2018-04-25","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311},{"amount":1000,"balance":15010,"createTime":"2018-04-25","direction":"OUT","reason":"EXCHANGECOUPON","remark":null,"userId":311}]
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
         * amount : 5
         * balance : 15
         * createTime : 2018-04-19
         * direction : IN
         * reason : SIGN
         * remark : null
         * userId : 311
         */

        private int amount;
        private int balance;
        private String createTime;
        private String direction;
        private String reason;
        private Object remark;
        private int userId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
