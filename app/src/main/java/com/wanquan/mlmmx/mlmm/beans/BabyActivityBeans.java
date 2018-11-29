package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class BabyActivityBeans implements Serializable {

    /**
     * data : {"babyMessage":[{"STATUS":0,"authority":1,"babyId":688,"childBirthDate":"2018-04-09 12:04:00","createTime":"2018-04-09 02:04:14","headIco":null,"height":null,"last_visit":"2018-04-09 04:04:51","nickName":"就拒绝","orderId":1,"preChildDate":null,"relationship":"爸爸","relativeCount":1,"sex":1,"updateTime":"2018-04-09 04:04:06","userId":265,"weight":null},{"STATUS":2,"authority":1,"babyId":689,"childBirthDate":"2018-04-08 12:04:00","createTime":"2018-04-09 02:04:44","headIco":null,"height":null,"last_visit":"2018-04-09 04:04:51","nickName":"得","orderId":1,"preChildDate":null,"relationship":"爸爸","relativeCount":1,"sex":1,"updateTime":"2018-04-09 02:04:44","userId":265,"weight":0}],"myBaby":2,"otherBaby":0}
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
         * babyMessage : [{"STATUS":0,"authority":1,"babyId":688,"childBirthDate":"2018-04-09 12:04:00","createTime":"2018-04-09 02:04:14","headIco":null,"height":null,"last_visit":"2018-04-09 04:04:51","nickName":"就拒绝","orderId":1,"preChildDate":null,"relationship":"爸爸","relativeCount":1,"sex":1,"updateTime":"2018-04-09 04:04:06","userId":265,"weight":null},{"STATUS":2,"authority":1,"babyId":689,"childBirthDate":"2018-04-08 12:04:00","createTime":"2018-04-09 02:04:44","headIco":null,"height":null,"last_visit":"2018-04-09 04:04:51","nickName":"得","orderId":1,"preChildDate":null,"relationship":"爸爸","relativeCount":1,"sex":1,"updateTime":"2018-04-09 02:04:44","userId":265,"weight":0}]
         * myBaby : 2
         * otherBaby : 0
         */

        private int myBaby;
        private int otherBaby;
        private List<BabyMessageBean> babyMessage;

        public int getMyBaby() {
            return myBaby;
        }

        public void setMyBaby(int myBaby) {
            this.myBaby = myBaby;
        }

        public int getOtherBaby() {
            return otherBaby;
        }

        public void setOtherBaby(int otherBaby) {
            this.otherBaby = otherBaby;
        }

        public List<BabyMessageBean> getBabyMessage() {
            return babyMessage;
        }

        public void setBabyMessage(List<BabyMessageBean> babyMessage) {
            this.babyMessage = babyMessage;
        }

        public static class BabyMessageBean {
            /**
             * STATUS : 0
             * authority : 1
             * babyId : 688
             * childBirthDate : 2018-04-09 12:04:00
             * createTime : 2018-04-09 02:04:14
             * headIco : null
             * height : null
             * last_visit : 2018-04-09 04:04:51
             * nickName : 就拒绝
             * orderId : 1
             * preChildDate : null
             * relationship : 爸爸
             * relativeCount : 1
             * sex : 1
             * updateTime : 2018-04-09 04:04:06
             * userId : 265
             * weight : null
             */

            private int STATUS;
            private int authority;
            private int babyId;
            private String childBirthDate;
            private String createTime;
            private Object headIco;
            private Object height;
            private String last_visit;
            private String nickName;
            private int orderId;
            private Object preChildDate;
            private String relationship;
            private int relativeCount;
            private int sex;
            private String updateTime;
            private int userId;
            private Object weight;

            public int getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(int STATUS) {
                this.STATUS = STATUS;
            }

            public int getAuthority() {
                return authority;
            }

            public void setAuthority(int authority) {
                this.authority = authority;
            }

            public int getBabyId() {
                return babyId;
            }

            public void setBabyId(int babyId) {
                this.babyId = babyId;
            }

            public String getChildBirthDate() {
                return childBirthDate;
            }

            public void setChildBirthDate(String childBirthDate) {
                this.childBirthDate = childBirthDate;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getHeadIco() {
                return headIco;
            }

            public void setHeadIco(Object headIco) {
                this.headIco = headIco;
            }

            public Object getHeight() {
                return height;
            }

            public void setHeight(Object height) {
                this.height = height;
            }

            public String getLast_visit() {
                return last_visit;
            }

            public void setLast_visit(String last_visit) {
                this.last_visit = last_visit;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public Object getPreChildDate() {
                return preChildDate;
            }

            public void setPreChildDate(Object preChildDate) {
                this.preChildDate = preChildDate;
            }

            public String getRelationship() {
                return relationship;
            }

            public void setRelationship(String relationship) {
                this.relationship = relationship;
            }

            public int getRelativeCount() {
                return relativeCount;
            }

            public void setRelativeCount(int relativeCount) {
                this.relativeCount = relativeCount;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public Object getWeight() {
                return weight;
            }

            public void setWeight(Object weight) {
                this.weight = weight;
            }
        }
    }
}
