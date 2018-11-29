package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class SignInBottonBeans implements Serializable {

    /**
     * data : [{"amount":2800,"content":"仅限购买美丽妈妈净化器使用","couponName":"美丽妈妈高端净化器优惠券","createTime":"2018-04-23 09:49:59","id":1,"minCost":20000,"price":2800,"rule":"满20000元可使用","status":0,"termDays":90,"updateTime":"2018-04-23 15:49:08"},{"amount":1000,"content":"仅限购买美丽妈妈净化器使用","couponName":"美丽妈妈高端净化器优惠券","createTime":"2018-04-23 09:49:59","id":2,"minCost":10000,"price":1000,"rule":"满10000元可使用","status":0,"termDays":90,"updateTime":"2018-04-23 15:49:04"},{"amount":500,"content":"仅限购买美丽妈妈净化器使用","couponName":"美丽妈妈高端净化器优惠券","createTime":"2018-04-23 09:49:59","id":3,"minCost":5000,"price":500,"rule":"满5000元可使用","status":0,"termDays":90,"updateTime":"2018-04-23 15:49:04"}]
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
         * amount : 2800
         * content : 仅限购买美丽妈妈净化器使用
         * couponName : 美丽妈妈高端净化器优惠券
         * createTime : 2018-04-23 09:49:59
         * id : 1
         * minCost : 20000
         * price : 2800
         * rule : 满20000元可使用
         * status : 0
         * termDays : 90
         * updateTime : 2018-04-23 15:49:08
         */

        private int amount;
        private String content;
        private String couponName;
        private String createTime;
        private int id;
        private int minCost;
        private int price;
        private String rule;
        private int status;
        private int termDays;
        private String updateTime;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMinCost() {
            return minCost;
        }

        public void setMinCost(int minCost) {
            this.minCost = minCost;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTermDays() {
            return termDays;
        }

        public void setTermDays(int termDays) {
            this.termDays = termDays;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
