package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class CircleFragmentThreessBeans implements Serializable {
    /**
     * data : [{"desc":"事业家庭一个都不能少","id":7,"img":"http://192.168.1.207:12001/img/circle/job-woman.png","isFollow":null,"name":"职场女性","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/job-woman.png"},{"desc":"聊尽天下性福事","id":8,"img":"http://192.168.1.207:12001/img/circle/both-sexes.png","isFollow":null,"name":"两性私房话","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/both-sexes"},{"desc":"不论你在哪里，我们都这等你","id":9,"img":"http://192.168.1.207:12001/img/circle/overseas_life.png","isFollow":null,"name":"海外生活讨论","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/overseas_life.png"},{"desc":"婆媳间那些事","id":10,"img":"http://192.168.1.207:12001/img/circle/mother-wife.png","isFollow":null,"name":"婆媳关系","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/mother-wife.png"},{"desc":"做最优秀的吃瓜群众","id":11,"img":"http://192.168.1.207:12001/img/circle/amusement.png","isFollow":null,"name":"娱乐八卦","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/amusement.png"},{"desc":"狗血故事，天天上演","id":12,"img":"http://192.168.1.207:12001/img/circle/marry-feeling.png","isFollow":null,"name":"情感婚姻","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/marry-feeling.png"},{"desc":"想聊就聊，开心就好","id":13,"img":"http://192.168.1.207:12001/img/circle/chat.png","isFollow":null,"name":"谈天说地","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/chat.png"}]
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
         * desc : 事业家庭一个都不能少
         * id : 7
         * img : http://192.168.1.207:12001/img/circle/job-woman.png
         * isFollow : null
         * name : 职场女性
         * parentId : 1
         * pic : http://192.168.1.207:12001/img/circle/job-woman.png
         */

        private String desc;
        private int id;
        private String img;
        private Object isFollow;
        private String name;
        private int parentId;
        private String pic;
        private boolean flag;

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(Object isFollow) {
            this.isFollow = isFollow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
