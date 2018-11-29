package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8.
 */

public class MyPrizeDiscountFragmentBeans implements Serializable {

    /**
     * data : [{"contact":null,"cost":500,"createTime":"2018-06-12 13:48:51","effectiveTime":"2018-06-12","expiredTime":"2018-07-22","id":299,"lotteryId":3,"lotteryName":"500元优惠券","price":null,"relativeId":15,"remark":"恭喜您获得500元优惠券！","status":0,"userCoupon":[{"amount":500,"content":"满8000减500","couponCode":"8635210818","couponId":15,"couponName":"500元优惠券","endTime":"2018-07-22","minCost":8000,"price":500,"rule":"满8000减500","startTime":"2018-06-12","status1":0,"status2":0,"termDays":30,"tip":"满8000减500"}]},{"contact":null,"cost":100,"createTime":"2018-06-12 13:48:46","effectiveTime":"2018-06-12","expiredTime":"2018-07-12","id":298,"lotteryId":8,"lotteryName":"100元优惠券","price":null,"relativeId":14,"remark":"恭喜您获得100元优惠券！","status":0,"userCoupon":[{"amount":100,"content":"满2000减100","couponCode":"0076603104","couponId":14,"couponName":"100元优惠券","endTime":"2018-07-12","minCost":2000,"price":100,"rule":"满2000减100","startTime":"2018-06-12","status1":0,"status2":0,"termDays":50,"tip":"满2000减100"}]},{"contact":null,"cost":100,"createTime":"2018-06-11 19:38:09","effectiveTime":"2018-06-11","expiredTime":"2018-07-11","id":281,"lotteryId":8,"lotteryName":"100元优惠券","price":null,"relativeId":14,"remark":"恭喜您获得100元优惠券！","status":0,"userCoupon":[{"amount":100,"content":"满2000减100","couponCode":"4878647367","couponId":14,"couponName":"100元优惠券","endTime":"2018-07-11","minCost":2000,"price":100,"rule":"满2000减100","startTime":"2018-06-11","status1":0,"status2":0,"termDays":50,"tip":"满2000减100"}]},{"contact":null,"cost":100,"createTime":"2018-06-11 19:35:29","effectiveTime":"2018-06-11","expiredTime":"2018-07-11","id":276,"lotteryId":8,"lotteryName":"100元优惠券","price":null,"relativeId":14,"remark":"恭喜您获得100元优惠券！","status":0,"userCoupon":[{"amount":100,"content":"满2000减100","couponCode":"4714774164","couponId":14,"couponName":"100元优惠券","endTime":"2018-07-11","minCost":2000,"price":100,"rule":"满2000减100","startTime":"2018-06-11","status1":0,"status2":0,"termDays":50,"tip":"满2000减100"}]}]
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
         * cost : 500
         * createTime : 2018-06-12 13:48:51
         * effectiveTime : 2018-06-12
         * expiredTime : 2018-07-22
         * id : 299
         * lotteryId : 3
         * lotteryName : 500元优惠券
         * price : null
         * relativeId : 15
         * remark : 恭喜您获得500元优惠券！
         * status : 0
         * userCoupon : [{"amount":500,"content":"满8000减500","couponCode":"8635210818","couponId":15,"couponName":"500元优惠券","endTime":"2018-07-22","minCost":8000,"price":500,"rule":"满8000减500","startTime":"2018-06-12","status1":0,"status2":0,"termDays":30,"tip":"满8000减500"}]
         */

        private Object contact;
        private int cost;
        private String createTime;
        private String effectiveTime;
        private String expiredTime;
        private int id;
        private int lotteryId;
        private String lotteryName;
        private Object price;
        private int relativeId;
        private String remark;
        private int status;
        private List<UserCouponBean> userCoupon;

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

        public String getEffectiveTime() {
            return effectiveTime;
        }

        public void setEffectiveTime(String effectiveTime) {
            this.effectiveTime = effectiveTime;
        }

        public String getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(String expiredTime) {
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

        public int getRelativeId() {
            return relativeId;
        }

        public void setRelativeId(int relativeId) {
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

        public List<UserCouponBean> getUserCoupon() {
            return userCoupon;
        }

        public void setUserCoupon(List<UserCouponBean> userCoupon) {
            this.userCoupon = userCoupon;
        }

        public static class UserCouponBean {
            /**
             * amount : 500
             * content : 满8000减500
             * couponCode : 8635210818
             * couponId : 15
             * couponName : 500元优惠券
             * endTime : 2018-07-22
             * minCost : 8000
             * price : 500
             * rule : 满8000减500
             * startTime : 2018-06-12
             * status1 : 0
             * status2 : 0
             * termDays : 30
             * tip : 满8000减500
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
}
