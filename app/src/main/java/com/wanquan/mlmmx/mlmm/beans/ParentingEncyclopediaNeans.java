package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XCF on 2018/9/14.
 */

public class ParentingEncyclopediaNeans implements Serializable {

    /**
     * data : [{"content":"","desc":"","icon":"","id":1,"title":"备孕"},{"content":"","desc":"","icon":"","id":2,"title":"怀孕"},{"content":"","desc":"","icon":"","id":3,"title":"分娩"},{"content":"","desc":"","icon":"","id":4,"title":"月子"},{"content":"","desc":"","icon":"","id":5,"title":"0-1岁"},{"content":"","desc":"","icon":"","id":6,"title":"1-3岁"},{"content":"","desc":"","icon":"","id":7,"title":"3-6岁"}]
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
         * content :
         * desc :
         * icon :
         * id : 1
         * title : 备孕
         */

        private String content;
        private String desc;
        private String icon;
        private int id;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
