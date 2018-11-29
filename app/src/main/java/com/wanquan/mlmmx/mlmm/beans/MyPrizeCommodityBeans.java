package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/29.
 */

public class MyPrizeCommodityBeans implements Serializable {

    /**
     * data : [{"contact":"领取商品时，请联系客服 025-68128446，兑换奖品！|最终解释权归美丽妈妈所有。","cost":0,"createTime":"2018-06-13 10:59:36","effectiveTime":null,"expiredTime":null,"id":322,"lotteryId":2,"lotteryName":"呼吸口罩","price":349,"relativeId":null,"remark":"恭喜您获得呼吸口罩一件！","status":0,"url":"http://192.168.1.207:12001/img/kouzhao.png","userCoupon":[]}]
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
         * contact : 领取商品时，请联系客服 025-68128446，兑换奖品！|最终解释权归美丽妈妈所有。
         * cost : 0
         * createTime : 2018-06-13 10:59:36
         * effectiveTime : null
         * expiredTime : null
         * id : 322
         * lotteryId : 2
         * lotteryName : 呼吸口罩
         * price : 349
         * relativeId : null
         * remark : 恭喜您获得呼吸口罩一件！
         * status : 0
         * url : http://192.168.1.207:12001/img/kouzhao.png
         * userCoupon : []
         */

        private String contact;
        private int cost;
        private String createTime;
        private Object effectiveTime;
        private Object expiredTime;
        private int id;
        private int lotteryId;
        private String lotteryName;
        private int price;
        private Object relativeId;
        private String remark;
        private int status;
        private String url;
        private List<?> userCoupon;

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<?> getUserCoupon() {
            return userCoupon;
        }

        public void setUserCoupon(List<?> userCoupon) {
            this.userCoupon = userCoupon;
        }
    }
}
