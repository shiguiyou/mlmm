package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/10.
 */

public class MainAirBeans implements Serializable{

    /**
     * data : {"commonParam":"","msg":"操作成功！","result":{"Humi":"43","PM10":"100","PM25":"18","Temp":"35","url":"https://cdn.heweather.com/cond_icon/101.png","weather":"多云"},"totalCount":0,"validatePass":true,"validateResult":1}
     * dynamicKey :
     * msg : 操作成功！
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
         * commonParam :
         * msg : 操作成功！
         * result : {"Humi":"43","PM10":"100","PM25":"18","Temp":"35","url":"https://cdn.heweather.com/cond_icon/101.png","weather":"多云"}
         * totalCount : 0
         * validatePass : true
         * validateResult : 1
         */

        private String commonParam;
        private String msg;
        private ResultBean result;
        private int totalCount;
        private boolean validatePass;
        private int validateResult;

        public String getCommonParam() {
            return commonParam;
        }

        public void setCommonParam(String commonParam) {
            this.commonParam = commonParam;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public boolean isValidatePass() {
            return validatePass;
        }

        public void setValidatePass(boolean validatePass) {
            this.validatePass = validatePass;
        }

        public int getValidateResult() {
            return validateResult;
        }

        public void setValidateResult(int validateResult) {
            this.validateResult = validateResult;
        }

        public static class ResultBean {
            /**
             * Humi : 43
             * PM10 : 100
             * PM25 : 18
             * Temp : 35
             * url : https://cdn.heweather.com/cond_icon/101.png
             * weather : 多云
             */

            private String Humi;
            private String PM10;
            private String PM25;
            private String Temp;
            private String url;
            private String weather;

            public String getHumi() {
                return Humi;
            }

            public void setHumi(String Humi) {
                this.Humi = Humi;
            }

            public String getPM10() {
                return PM10;
            }

            public void setPM10(String PM10) {
                this.PM10 = PM10;
            }

            public String getPM25() {
                return PM25;
            }

            public void setPM25(String PM25) {
                this.PM25 = PM25;
            }

            public String getTemp() {
                return Temp;
            }

            public void setTemp(String Temp) {
                this.Temp = Temp;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }
        }
    }
}
