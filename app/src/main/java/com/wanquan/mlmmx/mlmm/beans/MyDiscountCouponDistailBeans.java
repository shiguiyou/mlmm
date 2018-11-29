package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/24.
 */

public class MyDiscountCouponDistailBeans implements Serializable {

    /**
     * data : {"amount":2800,"content":"仅限购买美丽妈妈净化器使用","couponCode":"5508140815","couponId":1,"couponName":"美丽妈妈高端净化器优惠券","endTime":"2018-05-01","minCost":20000,"price":2800,"rule":"满20000元可使用|仅限购买美丽妈妈净化器使用|APP购买商品时，联系客服使用优惠券","startTime":"2018-04-01","status1":0,"status2":0,"termDays":0,"tip":"优惠券只能使用一次|优惠券不能叠加使用|优惠券使用后退款优惠券不退还"}
     * dynamicKey :
     * msg :
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
         * amount : 2800
         * content : 仅限购买美丽妈妈净化器使用
         * couponCode : 5508140815
         * couponId : 1
         * couponName : 美丽妈妈高端净化器优惠券
         * endTime : 2018-05-01
         * minCost : 20000
         * price : 2800
         * rule : 满20000元可使用|仅限购买美丽妈妈净化器使用|APP购买商品时，联系客服使用优惠券
         * startTime : 2018-04-01
         * status1 : 0
         * status2 : 0
         * termDays : 0
         * tip : 优惠券只能使用一次|优惠券不能叠加使用|优惠券使用后退款优惠券不退还
         */

        private int amount;
        private String content;
        private String couponCode;
        private int couponId;
        private String couponName;
        private String endTime;
        private int minCost;
        private int price;
        private String rule;
        private String startTime;
        private int status1;
        private int status2;
        private int termDays;
        private String tip;

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

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getStatus1() {
            return status1;
        }

        public void setStatus1(int status1) {
            this.status1 = status1;
        }

        public int getStatus2() {
            return status2;
        }

        public void setStatus2(int status2) {
            this.status2 = status2;
        }

        public int getTermDays() {
            return termDays;
        }

        public void setTermDays(int termDays) {
            this.termDays = termDays;
        }

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }
    }
}
