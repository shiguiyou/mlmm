package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12.
 */

public class CircleFragmentTwoBeans implements Serializable {
    /**
     * data : [{"circleId":8,"desc":"聊尽天下性福事","img":"http://192.168.1.207:12001/img/circle/both-sexes.png","isFollow":1,"joinTotal":5,"name":"两性私房话","pic":"http://192.168.1.207:12001/img/circle/both-sexes.png","userId":620},{"circleId":9,"desc":"不论你在哪里，我们都这等你","img":"http://192.168.1.207:12001/img/circle/overseas_life.png","isFollow":1,"joinTotal":4,"name":"海外生活讨论","pic":"http://192.168.1.207:12001/img/circle/overseas_life.png","userId":620},{"circleId":10,"desc":"婆媳间那些事","img":"http://192.168.1.207:12001/img/circle/mother-wife.png","isFollow":1,"joinTotal":5,"name":"婆媳关系","pic":"http://192.168.1.207:12001/img/circle/mother-wife.png","userId":620},{"circleId":11,"desc":"做最优秀的吃瓜群众","img":"http://192.168.1.207:12001/img/circle/amusement.png","isFollow":1,"joinTotal":4,"name":"娱乐八卦","pic":"http://192.168.1.207:12001/img/circle/amusement.png","userId":620},{"circleId":12,"desc":"狗血故事，天天上演","img":"http://192.168.1.207:12001/img/circle/marry-feeling.png","isFollow":1,"joinTotal":3,"name":"情感婚姻","pic":"http://192.168.1.207:12001/img/circle/marry-feeling.png","userId":620}]
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
         * circleId : 8
         * desc : 聊尽天下性福事
         * img : http://192.168.1.207:12001/img/circle/both-sexes.png
         * isFollow : 1
         * joinTotal : 5
         * name : 两性私房话
         * pic : http://192.168.1.207:12001/img/circle/both-sexes.png
         * userId : 620
         */

        private int circleId;
        private String desc;
        private String img;
        private int isFollow;
        private int joinTotal;
        private String name;
        private String pic;
        private int userId;

        public int getCircleId() {
            return circleId;
        }

        public void setCircleId(int circleId) {
            this.circleId = circleId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public int getJoinTotal() {
            return joinTotal;
        }

        public void setJoinTotal(int joinTotal) {
            this.joinTotal = joinTotal;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
