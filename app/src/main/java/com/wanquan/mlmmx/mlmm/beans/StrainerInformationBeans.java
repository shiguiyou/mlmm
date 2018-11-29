package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 */

public class StrainerInformationBeans implements Serializable {

    /**
     * data : [{"filterName":"初效滤网","filterType":"71","lifeTime":2000,"unusedTime":2000,"usedTime":0},{"filterName":"中效滤网","filterType":"72","lifeTime":2000,"unusedTime":2000,"usedTime":0},{"filterName":"高效滤网","filterType":"73","lifeTime":2000,"unusedTime":2000,"usedTime":0},{"filterName":"甲醛滤网","filterType":"74","lifeTime":2000,"unusedTime":2000,"usedTime":0},{"filterName":"臭氧滤网","filterType":"75","lifeTime":2000,"unusedTime":2000,"usedTime":0},{"filterName":"ifD模块滤网","filterType":"76","lifeTime":2000,"unusedTime":0,"usedTime":64000},{"filterName":"甲醛臭氧组合滤网","filterType":"77","lifeTime":2000,"unusedTime":2000,"usedTime":0}]
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
         * filterName : 初效滤网
         * filterType : 71
         * lifeTime : 2000
         * unusedTime : 2000
         * usedTime : 0
         */

        private String filterName;
        private String filterType;
        private int lifeTime;
        private int unusedTime;
        private int usedTime;

        public String getFilterName() {
            return filterName;
        }

        public void setFilterName(String filterName) {
            this.filterName = filterName;
        }

        public String getFilterType() {
            return filterType;
        }

        public void setFilterType(String filterType) {
            this.filterType = filterType;
        }

        public int getLifeTime() {
            return lifeTime;
        }

        public void setLifeTime(int lifeTime) {
            this.lifeTime = lifeTime;
        }

        public int getUnusedTime() {
            return unusedTime;
        }

        public void setUnusedTime(int unusedTime) {
            this.unusedTime = unusedTime;
        }

        public int getUsedTime() {
            return usedTime;
        }

        public void setUsedTime(int usedTime) {
            this.usedTime = usedTime;
        }
    }
}
