package com.wanquan.mlmmx.mlmm.beans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/27.
 */

public class BannerBeans {

    /**
     * data : [{"htmlUrl":"","id":1,"imgUrl":"http://imag.com/banner/banner1.png","isUsed":1,"oredrNum":1},{"htmlUrl":"","id":2,"imgUrl":"http://imag.com/banner/banner2.png","isUsed":1,"oredrNum":2}]
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
         * htmlUrl :
         * id : 1
         * imgUrl : http://imag.com/banner/banner1.png
         * isUsed : 1
         * oredrNum : 1
         */

        private String htmlUrl;
        private int id;
        private String imgUrl;
        private int isUsed;
        private int oredrNum;

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public void setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(int isUsed) {
            this.isUsed = isUsed;
        }

        public int getOredrNum() {
            return oredrNum;
        }

        public void setOredrNum(int oredrNum) {
            this.oredrNum = oredrNum;
        }
    }
}
