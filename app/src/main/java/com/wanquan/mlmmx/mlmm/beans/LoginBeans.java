package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by sgy on 2017/8/8.
 */

public class LoginBeans implements Serializable{
    /**
     * data : {"address":null,"appOs":null,"appOsVersion":null,"babyHeadIco":null,"babyHeight":null,"babyNickname":null,"babySex":null,"babyStatus":null,"babyWeight":null,"childBirthDate":null,"city":null,"createTime":"2018-06-11 09:12:37","headIco":"http://api.env365.cn/img/head/614_1807260824064620.png","integralAmount":122495,"integralBalance":113075,"lastLatitude":null,"lastLoginIp":"223.112.11.102","lastLoginTime":"2018-10-19 15:26:44","lastLongitude":null,"mobile":"17372205110","nickName":"乐乐","ownerUserId":614,"password":"96e79218965eb72c92a549dd5a330112","preChildDate":null,"province":null,"recommend":"88GJ","referee":null,"status":0,"token":"88acb0577a404bf09a835a4f3c8fd8ed","userLevel":6,"username":"17372205110"}
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
         * address : null
         * appOs : null
         * appOsVersion : null
         * babyHeadIco : null
         * babyHeight : null
         * babyNickname : null
         * babySex : null
         * babyStatus : null
         * babyWeight : null
         * childBirthDate : null
         * city : null
         * createTime : 2018-06-11 09:12:37
         * headIco : http://api.env365.cn/img/head/614_1807260824064620.png
         * integralAmount : 122495
         * integralBalance : 113075
         * lastLatitude : null
         * lastLoginIp : 223.112.11.102
         * lastLoginTime : 2018-10-19 15:26:44
         * lastLongitude : null
         * mobile : 17372205110
         * nickName : 乐乐
         * ownerUserId : 614
         * password : 96e79218965eb72c92a549dd5a330112
         * preChildDate : null
         * province : null
         * recommend : 88GJ
         * referee : null
         * status : 0
         * token : 88acb0577a404bf09a835a4f3c8fd8ed
         * userLevel : 6
         * username : 17372205110
         */

        private Object address;
        private Object appOs;
        private Object appOsVersion;
        private Object babyHeadIco;
        private Object babyHeight;
        private Object babyNickname;
        private Object babySex;
        private Object babyStatus;
        private Object babyWeight;
        private Object childBirthDate;
        private Object city;
        private String createTime;
        private String headIco;
        private int integralAmount;
        private int integralBalance;
        private Object lastLatitude;
        private String lastLoginIp;
        private String lastLoginTime;
        private Object lastLongitude;
        private String mobile;
        private String nickName;
        private int ownerUserId;
        private String password;
        private Object preChildDate;
        private Object province;
        private String recommend;
        private Object referee;
        private int status;
        private String token;
        private int userLevel;
        private String username;

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getAppOs() {
            return appOs;
        }

        public void setAppOs(Object appOs) {
            this.appOs = appOs;
        }

        public Object getAppOsVersion() {
            return appOsVersion;
        }

        public void setAppOsVersion(Object appOsVersion) {
            this.appOsVersion = appOsVersion;
        }

        public Object getBabyHeadIco() {
            return babyHeadIco;
        }

        public void setBabyHeadIco(Object babyHeadIco) {
            this.babyHeadIco = babyHeadIco;
        }

        public Object getBabyHeight() {
            return babyHeight;
        }

        public void setBabyHeight(Object babyHeight) {
            this.babyHeight = babyHeight;
        }

        public Object getBabyNickname() {
            return babyNickname;
        }

        public void setBabyNickname(Object babyNickname) {
            this.babyNickname = babyNickname;
        }

        public Object getBabySex() {
            return babySex;
        }

        public void setBabySex(Object babySex) {
            this.babySex = babySex;
        }

        public Object getBabyStatus() {
            return babyStatus;
        }

        public void setBabyStatus(Object babyStatus) {
            this.babyStatus = babyStatus;
        }

        public Object getBabyWeight() {
            return babyWeight;
        }

        public void setBabyWeight(Object babyWeight) {
            this.babyWeight = babyWeight;
        }

        public Object getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(Object childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public Object getLastLatitude() {
            return lastLatitude;
        }

        public void setLastLatitude(Object lastLatitude) {
            this.lastLatitude = lastLatitude;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public Object getLastLongitude() {
            return lastLongitude;
        }

        public void setLastLongitude(Object lastLongitude) {
            this.lastLongitude = lastLongitude;
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

        public int getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(int ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(Object preChildDate) {
            this.preChildDate = preChildDate;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public Object getReferee() {
            return referee;
        }

        public void setReferee(Object referee) {
            this.referee = referee;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
