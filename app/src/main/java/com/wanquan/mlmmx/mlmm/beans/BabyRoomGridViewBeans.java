package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19.
 */

public class BabyRoomGridViewBeans implements Serializable{


    /**
     * data : [{"cfs":[{"disable":false,"functionCode":"00,0A","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/on.png","name":"开","status":true},{"disable":false,"functionCode":"00,0B","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/off.png","name":"关","status":false}],"disable":false,"functionCode":"81","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/on.png","name":"开"},{"cfs":[{"disable":false,"functionCode":"00,0B","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/standby.png","name":"0档/待机","status":false},{"disable":false,"functionCode":"00,10","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemin.png","name":"1档","status":false},{"disable":false,"functionCode":"00,11","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemin.png","name":"2档","status":false},{"disable":false,"functionCode":"00,12","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemid.png","name":"3档","status":false},{"disable":false,"functionCode":"00,13","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemid.png","name":"4档","status":false},{"disable":false,"functionCode":"00,14","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemax.png","name":"5档","status":true}],"disable":false,"functionCode":"84","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/volumemax.png","name":"5档"},{"cfs":[{"disable":false,"functionCode":"00,3A","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/auto.png","name":"自动模式","status":false},{"disable":false,"functionCode":"00,3C","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/humual.png","name":"手动模式","status":true},{"disable":false,"functionCode":"00,3B","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/sleep.png","name":"睡眠模式","status":false}],"disable":false,"functionCode":"91","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/humual.png","name":"手动模式"},{"cfs":[{"disable":false,"functionCode":"00,2C","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/childlock.png","name":"锁定","status":true},{"disable":false,"functionCode":"00,2D","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/childlockoff.png","name":"解锁","status":false}],"disable":false,"functionCode":"92","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/childlock.png","name":"锁定"},{"cfs":[{"disable":false,"functionCode":"00,1B","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时功能关","status":true},{"disable":false,"functionCode":"00,47","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时1小时","status":false},{"disable":false,"functionCode":"00,48","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时2小时","status":false},{"disable":false,"functionCode":"00,49","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时4小时","status":false},{"disable":false,"functionCode":"00,4A","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时8小时","status":false}],"disable":false,"functionCode":"85","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/timeset.png","name":"定时功能关"}]
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
         * cfs : [{"disable":false,"functionCode":"00,0A","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/on.png","name":"开","status":true},{"disable":false,"functionCode":"00,0B","ico":"http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/off.png","name":"关","status":false}]
         * disable : false
         * functionCode : 81
         * ico : http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/on.png
         * name : 开
         */

        private boolean disable;
        private String functionCode;
        private String ico;
        private String name;
        private List<CfsBean> cfs;

        public boolean isDisable() {
            return disable;
        }

        public void setDisable(boolean disable) {
            this.disable = disable;
        }

        public String getFunctionCode() {
            return functionCode;
        }

        public void setFunctionCode(String functionCode) {
            this.functionCode = functionCode;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CfsBean> getCfs() {
            return cfs;
        }

        public void setCfs(List<CfsBean> cfs) {
            this.cfs = cfs;
        }

        public static class CfsBean {
            /**
             * disable : false
             * functionCode : 00,0A
             * ico : http://mlmm.oss-cn-shanghai.aliyuncs.com//img/controtypeimg1/on.png
             * name : 开
             * status : true
             */

            private boolean disable;
            private String functionCode;
            private String ico;
            private String name;
            private boolean status;

            public boolean isDisable() {
                return disable;
            }

            public void setDisable(boolean disable) {
                this.disable = disable;
            }

            public String getFunctionCode() {
                return functionCode;
            }

            public void setFunctionCode(String functionCode) {
                this.functionCode = functionCode;
            }

            public String getIco() {
                return ico;
            }

            public void setIco(String ico) {
                this.ico = ico;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }
        }
    }

    @Override
    public String toString() {
        return "BabyRoomGridViewBeans{" +
                "dynamicKey='" + dynamicKey + '\'' +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                ", resultCode=" + resultCode +
                ", token='" + token + '\'' +
                ", data=" + data +
                '}';
    }
}
