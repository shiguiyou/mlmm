package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by XCF on 2018/9/14.
 */

public class RemoteUpgradeOneBeans implements Serializable {
    /**
     * data : {"deviceCode":"0F000016","promptMsg":"当前版本为1.0.0，可以升级到新版本1.0.1，是否进行升级？","upgradeContent":"加入了xxx新功能...","upgradeStatus":1,"upgraded":true}
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
         * deviceCode : 0F000016
         * promptMsg : 当前版本为1.0.0，可以升级到新版本1.0.1，是否进行升级？
         * upgradeContent : 加入了xxx新功能...
         * upgradeStatus : 1
         * upgraded : true
         */

        private String deviceCode;
        private String promptMsg;
        private String upgradeContent;
        private int upgradeStatus;
        private boolean upgraded;

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getPromptMsg() {
            return promptMsg;
        }

        public void setPromptMsg(String promptMsg) {
            this.promptMsg = promptMsg;
        }

        public String getUpgradeContent() {
            return upgradeContent;
        }

        public void setUpgradeContent(String upgradeContent) {
            this.upgradeContent = upgradeContent;
        }

        public int getUpgradeStatus() {
            return upgradeStatus;
        }

        public void setUpgradeStatus(int upgradeStatus) {
            this.upgradeStatus = upgradeStatus;
        }

        public boolean isUpgraded() {
            return upgraded;
        }

        public void setUpgraded(boolean upgraded) {
            this.upgraded = upgraded;
        }
    }
}
