package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MoreServiceBeans implements Serializable {
    /**
     * data : {"toolbarList1":[{"id":3,"name":"发育变化","optional":1,"order":3,"status":"1,2","type":0,"userId":null},{"id":6,"name":"数胎动","optional":1,"order":6,"status":"1","type":0,"userId":null},{"id":7,"name":"产检提醒","optional":1,"order":7.5,"status":"1","type":0,"userId":null},{"id":8,"name":"孕期食谱","optional":1,"order":8,"status":"1","type":0,"userId":null},{"id":9,"name":"看懂B超单","optional":1,"order":9,"status":"1","type":0,"userId":null}],"toolbarList2":[{"id":4,"name":"宝宝听听","optional":0,"order":4,"status":"0;1;2","type":1,"userId":null},{"id":10,"name":"亲子任务","optional":1,"order":10,"status":"2","type":1,"userId":null},{"id":11,"name":"成长曲线","optional":1,"order":11,"status":"2","type":1,"userId":null},{"id":12,"name":"疫苗提醒","optional":1,"order":12,"status":"2","type":1,"userId":null},{"id":13,"name":"月子餐","optional":1,"order":13,"status":"2","type":1,"userId":null}],"toolbarList3":[{"id":1,"name":"宝宝相册","optional":0,"order":1,"status":"0;1;2","type":2,"userId":null},{"id":2,"name":"情感日志","optional":0,"order":2,"status":"0;1;2","type":2,"userId":null},{"id":5,"name":"亲子游戏","optional":0,"order":5,"status":"0;1;2","type":2,"userId":null},{"id":14,"name":"能不能吃","optional":1,"order":14,"status":"0;1;2","type":2,"userId":null}]}
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
        private List<ToolbarList1Bean> toolbarList1;
        private List<ToolbarList2Bean> toolbarList2;
        private List<ToolbarList3Bean> toolbarList3;

        public List<ToolbarList1Bean> getToolbarList1() {
            return toolbarList1;
        }

        public void setToolbarList1(List<ToolbarList1Bean> toolbarList1) {
            this.toolbarList1 = toolbarList1;
        }

        public List<ToolbarList2Bean> getToolbarList2() {
            return toolbarList2;
        }

        public void setToolbarList2(List<ToolbarList2Bean> toolbarList2) {
            this.toolbarList2 = toolbarList2;
        }

        public List<ToolbarList3Bean> getToolbarList3() {
            return toolbarList3;
        }

        public void setToolbarList3(List<ToolbarList3Bean> toolbarList3) {
            this.toolbarList3 = toolbarList3;
        }

        public static class ToolbarList1Bean {
            /**
             * id : 3
             * name : 发育变化
             * optional : 1
             * order : 3
             * status : 1,2
             * type : 0
             * userId : null
             */

            private String id;
            private String name;
            private String optional;
            private String order;
            private String status;
            private int type;
            private Object userId;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOptional() {
                return optional;
            }

            public void setOptional(String optional) {
                this.optional = optional;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }
        }

        public static class ToolbarList2Bean {
            /**
             * id : 4
             * name : 宝宝听听
             * optional : 0
             * order : 4
             * status : 0;1;2
             * type : 1
             * userId : null
             */

            private String id;
            private String name;
            private String optional;
            private String order;
            private String status;
            private int type;
            private Object userId;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOptional() {
                return optional;
            }

            public void setOptional(String optional) {
                this.optional = optional;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }
        }

        public static class ToolbarList3Bean {
            /**
             * id : 1
             * name : 宝宝相册
             * optional : 0
             * order : 1
             * status : 0;1;2
             * type : 2
             * userId : null
             */

            private String id;
            private String name;
            private String optional;
            private String order;
            private String status;
            private int type;
            private Object userId;
            private boolean flag;

            public boolean isFlag() {
                return flag;
            }

            public void setFlag(boolean flag) {
                this.flag = flag;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOptional() {
                return optional;
            }

            public void setOptional(String optional) {
                this.optional = optional;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getUserId() {
                return userId;
            }

            public void setUserId(Object userId) {
                this.userId = userId;
            }
        }
    }
}
