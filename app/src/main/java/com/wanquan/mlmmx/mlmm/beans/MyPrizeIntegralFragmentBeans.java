package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class MyPrizeIntegralFragmentBeans implements Serializable {

    /**
     * data : [{"contact":null,"cost":10,"createTime":"0000-00-00 00:00:00","effectiveTime":null,"expiredTime":null,"id":81,"lotteryId":1,"lotteryName":"10积分","price":null,"relativeId":null,"remark":"恭喜您获得10积分奖励！","status":0,"userCoupon":[]}]
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
         * contact : null
         * cost : 10
         * createTime : 0000-00-00 00:00:00
         * effectiveTime : null
         * expiredTime : null
         * id : 81
         * lotteryId : 1
         * lotteryName : 10积分
         * price : null
         * relativeId : null
         * remark : 恭喜您获得10积分奖励！
         * status : 0
         * userCoupon : []
         */

        private Object contact;
        private int cost;
        private String createTime;
        private Object effectiveTime;
        private Object expiredTime;
        private int id;
        private int lotteryId;
        private String lotteryName;
        private Object price;
        private Object relativeId;
        private String remark;
        private int status;
        private List<?> userCoupon;

        public Object getContact() {
            return contact;
        }

        public void setContact(Object contact) {
            this.contact = contact;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getEffectiveTime() {
            return effectiveTime;
        }

        public void setEffectiveTime(Object effectiveTime) {
            this.effectiveTime = effectiveTime;
        }

        public Object getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(Object expiredTime) {
            this.expiredTime = expiredTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLotteryId() {
            return lotteryId;
        }

        public void setLotteryId(int lotteryId) {
            this.lotteryId = lotteryId;
        }

        public String getLotteryName() {
            return lotteryName;
        }

        public void setLotteryName(String lotteryName) {
            this.lotteryName = lotteryName;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getRelativeId() {
            return relativeId;
        }

        public void setRelativeId(Object relativeId) {
            this.relativeId = relativeId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<?> getUserCoupon() {
            return userCoupon;
        }

        public void setUserCoupon(List<?> userCoupon) {
            this.userCoupon = userCoupon;
        }
    }
}
