package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/23.
 */

public class UpDataInfoBeans implements Serializable {
    /**
     * update : {"versionName":"3.3.1","versionCode":"4","focusUpdate":"true","downloadUrl":"http://api.env365.cn/app-arm-release.apk"}
     */

    private UpdateBean update;

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {
        /**
         * versionName : 3.3.1
         * versionCode : 4
         * focusUpdate : true
         * downloadUrl : http://api.env365.cn/app-arm-release.apk
         */

        private String versionName;
        private String versionCode;
        private String focusUpdate;
        private String downloadUrl;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getFocusUpdate() {
            return focusUpdate;
        }

        public void setFocusUpdate(String focusUpdate) {
            this.focusUpdate = focusUpdate;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
    }
}


