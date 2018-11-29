package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyIntegralBeans implements Serializable {

    /**
     * data : [{"amount":2800,"content":"仅限购买美丽妈妈净化器使用","couponName":"美丽妈妈高端净化器优惠券","createTime":"2018-04-23 09:49:59","endTime":"2018-05-01","id":1,"minCost":20000,"price":2800,"rule":"满20000元可使用|仅限购买美丽妈妈净化器使用|APP购买商品时，联系客服使用优惠券","startTime":"2018-04-01","status":0,"termDays":0,"tip":"优惠券只能使用一次|优惠券不能叠加使用|优惠券使用后退款优惠券不退还","updateTime":"2018-04-23 15:49:08"},{"amount":1000,"content":"仅限购买美丽妈妈净化器使用","couponName":"美丽妈妈高端净化器优惠券","createTime":"2018-04-23 09:49:59","endTime":null,"id":2,"minCost":10000,"price":1000,"rule":"满20000元可使用|仅限购买美丽妈妈净化器使用|APP购买商品时，联系客服使用优惠券","startTime":null,"status":0,"termDays":90,"tip":"优惠券只能使用一次|优惠券不能叠加使用|优惠券使用后退款优惠券不退还","updateTime":"2018-04-23 15:49:04"}]
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
         * endTime : 2018-05-01
         * id : 1
         * minCost : 20000
         * price : 2800
         * rule : 满20000元可使用|仅限购买美丽妈妈净化器使用|APP购买商品时，联系客服使用优惠券
         * startTime : 2018-04-01
         * status : 0
         * termDays : 0
         * tip : 优惠券只能使用一次|优惠券不能叠加使用|优惠券使用后退款优惠券不退还
         * updateTime : 2018-04-23 15:49:08
         */

        private int amount;
        private String content;
        private String couponName;
        private String createTime;
        private String endTime;
        private int id;
        private int minCost;
        private int price;
        private String rule;
        private String startTime;
        private int status;
        private int termDays;
        private String tip;
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

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
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

        public String getTip() {
            return tip;
        }

        public void setTip(String tip) {
            this.tip = tip;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
