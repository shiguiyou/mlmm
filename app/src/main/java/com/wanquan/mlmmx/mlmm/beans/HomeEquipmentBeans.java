package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public class HomeEquipmentBeans implements Serializable{
    /**
     * data : [{"ctlStatus":"00,0A","ctlType":"81","deviceCode":"0F00000F","deviceId":28,"deviceName":"美丽妈妈净化器","deviceType":"0F","image":"","isOn":1,"isOnLine":1,"pm25Value":12,"thumbNail":""},{"ctlStatus":"00,0B","ctlType":"81","deviceCode":"04000001","deviceId":7,"deviceName":"车载净化器","deviceType":"04","image":"http://mis.env365.cn/architecture-mis/device/pic_1808030242106077.png","isOn":0,"isOnLine":0,"pm25Value":49,"thumbNail":"http://mis.env365.cn/architecture-mis/device/pic_1808030242108028.png"}]
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
         * ctlStatus : 00,0A
         * ctlType : 81
         * deviceCode : 0F00000F
         * deviceId : 28
         * deviceName : 美丽妈妈净化器
         * deviceType : 0F
         * image :
         * isOn : 1
         * isOnLine : 1
         * pm25Value : 12
         * thumbNail :
         */

        private String babyId;
        private String ctlStatus;
        private String ctlType;
        private String deviceCode;
        private int deviceId;
        private String deviceName;
        private String deviceType;
        private String ownerUserId;
        private String image;
        private int isOn;
        private int isOnLine;
        private int pm25Value;
        private String thumbNail;

        public String getBabyId() {
            return babyId;
        }

        public void setBabyId(String babyId) {
            this.babyId = babyId;
        }

        public String getOwnerUserId() {
            return ownerUserId;
        }

        public void setOwnerUserId(String ownerUserId) {
            this.ownerUserId = ownerUserId;
        }

        public String getCtlStatus() {
            return ctlStatus;
        }

        public void setCtlStatus(String ctlStatus) {
            this.ctlStatus = ctlStatus;
        }

        public String getCtlType() {
            return ctlType;
        }

        public void setCtlType(String ctlType) {
            this.ctlType = ctlType;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getIsOn() {
            return isOn;
        }

        public void setIsOn(int isOn) {
            this.isOn = isOn;
        }

        public int getIsOnLine() {
            return isOnLine;
        }

        public void setIsOnLine(int isOnLine) {
            this.isOnLine = isOnLine;
        }

        public int getPm25Value() {
            return pm25Value;
        }

        public void setPm25Value(int pm25Value) {
            this.pm25Value = pm25Value;
        }

        public String getThumbNail() {
            return thumbNail;
        }

        public void setThumbNail(String thumbNail) {
            this.thumbNail = thumbNail;
        }
    }
}
