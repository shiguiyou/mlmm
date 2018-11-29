package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/28.
 */

public class HomeQieHuanBeans implements Serializable {
    /**
     * data : {"babyHeadIco":"","babyHeight":"","babyNickname":"解决","babySex":1,"babyStatus":2,"babyWeight":0,"childBirthDate":"2018-01-01 00:00:00","create_time":"2018-02-28 16:11:22","id":638,"preChildDate":"","relationship":"爸爸","relative_count":1,"update_time":"2018-02-28 16:11:22"}
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
         * babyNickname : 解决
         * babySex : 1
         * babyStatus : 2
         * babyWeight : 0
         * childBirthDate : 2018-01-01 00:00:00
         * create_time : 2018-02-28 16:11:22
         * id : 638
         * preChildDate :
         * relationship : 爸爸
         * relative_count : 1
         * update_time : 2018-02-28 16:11:22
         */

        private String babyHeadIco;
        private String babyHeight;
        private String babyNickname;
        private int babySex;
        private int babyStatus;
        private int babyWeight;
        private String childBirthDate;
        private String create_time;
        private int id;
        private String preChildDate;
        private String relationship;
        private int relative_count;
        private String update_time;

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

        public int getBabySex() {
            return babySex;
        }

        public void setBabySex(int babySex) {
            this.babySex = babySex;
        }

        public int getBabyStatus() {
            return babyStatus;
        }

        public void setBabyStatus(int babyStatus) {
            this.babyStatus = babyStatus;
        }

        public int getBabyWeight() {
            return babyWeight;
        }

        public void setBabyWeight(int babyWeight) {
            this.babyWeight = babyWeight;
        }

        public String getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(String childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(String preChildDate) {
            this.preChildDate = preChildDate;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public int getRelative_count() {
            return relative_count;
        }

        public void setRelative_count(int relative_count) {
            this.relative_count = relative_count;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
