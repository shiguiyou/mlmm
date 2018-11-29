package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class MyCollectionBeans implements Serializable {

    /**
     * data : [{"childBirthDate":1481790115000,"commentCount":12,"content":"从 v","createTime":"2017-12-15","follow":57,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","id":57,"img":"forum/101_1712150900320063.png","message":"宝宝1年9天","nickName":"add","preChildDate":1514044800000,"title":"南京","type":"食物","updateTime":"2017-12-15","userId":101,"userName":"15261825221"},{"childBirthDate":1481790115000,"commentCount":3,"content":"上海","createTime":"2017-12-15","follow":56,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","id":56,"img":null,"message":"宝宝1年9天","nickName":"add","preChildDate":1514044800000,"title":"三个","type":"食物","updateTime":"2017-12-15","userId":101,"userName":"15261825221"},{"childBirthDate":1483200000000,"commentCount":null,"content":"方套yhhhhh眨眼睛上不了线","createTime":"2017-12-18","follow":60,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":60,"img":"forum/103_1712180427224432.png|forum/60_1712180427238536.png|forum/60_1712180427235292.png|forum/60_1712180427238573.png","message":"宝宝11个月24天","nickName":"17372205119","preChildDate":1514649600000,"title":"应该就有","type":"坐月子","updateTime":"2017-12-18","userId":103,"userName":"17372205119"},{"childBirthDate":1483200000000,"commentCount":21,"content":"gfggg","createTime":"2017-12-18","follow":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":61,"img":"forum/103_1712180537099732.png|forum/61_1712180537105987.png|forum/61_1712180537107699.png|forum/61_1712180537100571.png|forum/61_1712180537115042.png|forum/61_1712180537116494.png","message":"宝宝11个月24天","nickName":"17372205119","preChildDate":1514649600000,"title":"敢了","type":"坐月子","updateTime":"2017-12-18","userId":103,"userName":"17372205119"},{"childBirthDate":1481790115000,"commentCount":49,"content":"Aaaaaaaaaaaaa","createTime":"2017-12-20","follow":63,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","id":63,"img":null,"message":"宝宝1年9天","nickName":"add","preChildDate":1514044800000,"title":"aaaaaaaaaaaaa","type":"孕育知识","updateTime":"2017-12-20","userId":101,"userName":"15261825221"}]
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
         * childBirthDate : 1481790115000
         * commentCount : 12
         * content : 从 v
         * createTime : 2017-12-15
         * follow : 57
         * headIco : http://api.env365.cn/img/head/101_1711290829318711.png
         * id : 57
         * img : forum/101_1712150900320063.png
         * message : 宝宝1年9天
         * nickName : add
         * preChildDate : 1514044800000
         * title : 南京
         * type : 食物
         * updateTime : 2017-12-15
         * userId : 101
         * userName : 15261825221
         */

        private long childBirthDate;
        private int commentCount;
        private String content;
        private String createTime;
        private int follow;
        private String headIco;
        private int id;
        private String img;
        private String message;
        private String nickName;
        private long preChildDate;
        private String title;
        private String type;
        private String updateTime;
        private int userId;
        private String userName;

        public long getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(long childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
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

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public long getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(long preChildDate) {
            this.preChildDate = preChildDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
