package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13.
 */

public class MyFenSiActivityBeans implements Serializable{
    /**
     * data : [{"babyInfo":[{"babyId":10,"babyName":"昵称","babyStatus":0,"childBirthDate":"2017-03-03","indexNum":1,"preChildDate":"2018-01-27"}],"fansName":"宝妈妈","fid":1,"headIco":"http://api.env365.cn/img/head/1_1803030716403772.png","id":77,"mutual":false,"uid":null},{"babyInfo":[{"babyId":9,"babyName":"哈哈哈哈哈","babyStatus":0,"childBirthDate":"2017-01-01","indexNum":1,"preChildDate":"2018-05-01"}],"fansName":"美丽","fid":61,"headIco":"http://api.env365.cn/img/head/61_1805310426518955.png","id":136,"mutual":false,"uid":null},{"babyInfo":[{"babyId":25,"babyName":null,"babyStatus":0,"childBirthDate":null,"indexNum":1,"preChildDate":null}],"fansName":"小天才","fid":620,"headIco":"http://192.168.1.207/img/head/imgdefault@2x.png","id":137,"mutual":false,"uid":null}]
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
         * babyInfo : [{"babyId":10,"babyName":"昵称","babyStatus":0,"childBirthDate":"2017-03-03","indexNum":1,"preChildDate":"2018-01-27"}]
         * fansName : 宝妈妈
         * fid : 1
         * headIco : http://api.env365.cn/img/head/1_1803030716403772.png
         * id : 77
         * mutual : false
         * uid : null
         */

        private String fansName;
        private int fid;
        private String headIco;
        private int id;
        private boolean mutual;
        private String uid;
        private List<BabyInfoBean> babyInfo;

        public String getFansName() {
            return fansName;
        }

        public void setFansName(String fansName) {
            this.fansName = fansName;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isMutual() {
            return mutual;
        }

        public void setMutual(boolean mutual) {
            this.mutual = mutual;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public List<BabyInfoBean> getBabyInfo() {
            return babyInfo;
        }

        public void setBabyInfo(List<BabyInfoBean> babyInfo) {
            this.babyInfo = babyInfo;
        }

        public static class BabyInfoBean {
            /**
             * babyId : 10
             * babyName : 昵称
             * babyStatus : 0
             * childBirthDate : 2017-03-03
             * indexNum : 1
             * preChildDate : 2018-01-27
             */

            private int babyId;
            private String babyName;
            private int babyStatus;
            private String childBirthDate;
            private int indexNum;
            private String preChildDate;

            public int getBabyId() {
                return babyId;
            }

            public void setBabyId(int babyId) {
                this.babyId = babyId;
            }

            public String getBabyName() {
                return babyName;
            }

            public void setBabyName(String babyName) {
                this.babyName = babyName;
            }

            public int getBabyStatus() {
                return babyStatus;
            }

            public void setBabyStatus(int babyStatus) {
                this.babyStatus = babyStatus;
            }

            public String getChildBirthDate() {
                return childBirthDate;
            }

            public void setChildBirthDate(String childBirthDate) {
                this.childBirthDate = childBirthDate;
            }

            public int getIndexNum() {
                return indexNum;
            }

            public void setIndexNum(int indexNum) {
                this.indexNum = indexNum;
            }

            public String getPreChildDate() {
                return preChildDate;
            }

            public void setPreChildDate(String preChildDate) {
                this.preChildDate = preChildDate;
            }
        }
    }
}
