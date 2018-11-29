package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by Administrator on 2017/9/19.
 */

public class PersonalinformationActivityBeans {
    /**
     * data : {"babyHeadIco":"","babyHeight":"","babyNickname":"","babySex":"","babyStatus":"","babyWeight":"","childBirthDate":"","fansCount":0,"forumCount":29,"hasUnReadComment":false,"hasUnReadMsg":false,"headIco":"http://api3.env365.cn/img/head/imgdefault@2x.png","integralAmount":642,"integralBalance":534,"mobile":"17372205110","nickName":"美丽妈妈用户987773","preChildDate":"","referee":"13913016920","userCouponTotal":2,"userLevel":3}
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
         * babyHeadIco :
         * babyHeight :
         * babyNickname :
         * babySex :
         * babyStatus :
         * babyWeight :
         * childBirthDate :
         * fansCount : 0
         * forumCount : 29
         * hasUnReadComment : false
         * hasUnReadMsg : false
         * headIco : http://api3.env365.cn/img/head/imgdefault@2x.png
         * integralAmount : 642
         * integralBalance : 534
         * mobile : 17372205110
         * nickName : 美丽妈妈用户987773
         * preChildDate :
         * referee : 13913016920
         * userCouponTotal : 2
         * userLevel : 3
         */

        private String babyHeadIco;
        private String babyHeight;
        private String babyNickname;
        private String babySex;
        private String babyStatus;
        private String babyWeight;
        private String childBirthDate;
        private int fansCount;
        private int forumCount;
        private boolean hasUnReadComment;
        private boolean hasUnReadMsg;
        private String headIco;
        private int integralAmount;
        private int integralBalance;
        private String mobile;
        private String nickName;
        private String preChildDate;
        private String referee;
        private int userCouponTotal;
        private int userLevel;

        public String getBabyHeadIco() {
            return babyHeadIco;
        }

        public void setBabyHeadIco(String babyHeadIco) {
            this.babyHeadIco = babyHeadIco;
        }

        public String getBabyHeight() {
            return babyHeight;
        }

        public void setBabyHeight(String babyHeight) {
            this.babyHeight = babyHeight;
        }

        public String getBabyNickname() {
            return babyNickname;
        }

        public void setBabyNickname(String babyNickname) {
            this.babyNickname = babyNickname;
        }

        public String getBabySex() {
            return babySex;
        }

        public void setBabySex(String babySex) {
            this.babySex = babySex;
        }

        public String getBabyStatus() {
            return babyStatus;
        }

        public void setBabyStatus(String babyStatus) {
            this.babyStatus = babyStatus;
        }

        public String getBabyWeight() {
            return babyWeight;
        }

        public void setBabyWeight(String babyWeight) {
            this.babyWeight = babyWeight;
        }

        public String getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(String childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public int getFansCount() {
            return fansCount;
        }

        public void setFansCount(int fansCount) {
            this.fansCount = fansCount;
        }

        public int getForumCount() {
            return forumCount;
        }

        public void setForumCount(int forumCount) {
            this.forumCount = forumCount;
        }

        public boolean isHasUnReadComment() {
            return hasUnReadComment;
        }

        public void setHasUnReadComment(boolean hasUnReadComment) {
            this.hasUnReadComment = hasUnReadComment;
        }

        public boolean isHasUnReadMsg() {
            return hasUnReadMsg;
        }

        public void setHasUnReadMsg(boolean hasUnReadMsg) {
            this.hasUnReadMsg = hasUnReadMsg;
        }

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
        }

        public int getIntegralAmount() {
            return integralAmount;
        }

        public void setIntegralAmount(int integralAmount) {
            this.integralAmount = integralAmount;
        }

        public int getIntegralBalance() {
            return integralBalance;
        }

        public void setIntegralBalance(int integralBalance) {
            this.integralBalance = integralBalance;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(String preChildDate) {
            this.preChildDate = preChildDate;
        }

        public String getReferee() {
            return referee;
        }

        public void setReferee(String referee) {
            this.referee = referee;
        }

        public int getUserCouponTotal() {
            return userCouponTotal;
        }

        public void setUserCouponTotal(int userCouponTotal) {
            this.userCouponTotal = userCouponTotal;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }
    }
}
