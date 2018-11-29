package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class CircleFragmentThreeBeans implements Serializable {

    /**
     * data : [{"desc":null,"id":1,"img":null,"name":"八卦","parent_id":null,"pic":""},{"desc":null,"id":2,"img":null,"name":"兴趣","parent_id":null,"pic":""},{"desc":null,"id":3,"img":null,"name":"时尚","parent_id":null,"pic":""},{"desc":null,"id":4,"img":null,"name":"育儿","parent_id":null,"pic":""},{"desc":null,"id":5,"img":null,"name":"教育","parent_id":null,"pic":""},{"desc":null,"id":6,"img":null,"name":"好孕","parent_id":null,"pic":""}]
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
         * desc : null
         * id : 1
         * img : null
         * name : 八卦
         * parent_id : null
         * pic :
         */

        private Object desc;
        private int id;
        private Object img;
        private String name;
        private Object parent_id;
        private String pic;

        public Object getDesc() {
            return desc;
        }

        public void setDesc(Object desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getParent_id() {
            return parent_id;
        }

        public void setParent_id(Object parent_id) {
            this.parent_id = parent_id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
