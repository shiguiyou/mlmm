package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/1.
 */

public class MessageReplayCircleDetailsBenas implements Serializable {

    /**
     * data : {"childBirthDate":null,"commentCount":6,"content":"每个夜晚的床头，那盏温馨的灯下，孩子靠在父母怀里，听他们讲故事。这是孩子成长中最幸福的时光。\n\n科学研究显示，经常读书给婴儿听，可以让他们与父母间建立特殊情感。婴儿喜欢听到熟悉的声音，而书中的文字、图画和故事更能刺激他们的脑部发育。","createTime":"2018-07-27","follow":null,"headIco":"http://api.env365.cn/img/head/614_1807260824064620.png","id":434,"img":"http://api.env365.cn/forum/614_1807270600259362.png|","nickName":"乐乐","preChildDate":null,"readTimes":4,"title":"每天睡前讲故事","type":"孕育知识","updateTime":"2018-07-27","userId":614,"userName":"17372205110"}
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
         * childBirthDate : null
         * commentCount : 6
         * content : 每个夜晚的床头，那盏温馨的灯下，孩子靠在父母怀里，听他们讲故事。这是孩子成长中最幸福的时光。

         科学研究显示，经常读书给婴儿听，可以让他们与父母间建立特殊情感。婴儿喜欢听到熟悉的声音，而书中的文字、图画和故事更能刺激他们的脑部发育。
         * createTime : 2018-07-27
         * follow : null
         * headIco : http://api.env365.cn/img/head/614_1807260824064620.png
         * id : 434
         * img : http://api.env365.cn/forum/614_1807270600259362.png|
         * nickName : 乐乐
         * preChildDate : null
         * readTimes : 4
         * title : 每天睡前讲故事
         * type : 孕育知识
         * updateTime : 2018-07-27
         * userId : 614
         * userName : 17372205110
         */

        private Object childBirthDate;
        private int commentCount;
        private String content;
        private String createTime;
        private int follow;
        private String headIco;
        private int id;
        private String img;
        private String nickName;
        private Object preChildDate;
        private int readTimes;
        private String title;
        private String type;
        private String updateTime;
        private int userId;
        private String userName;

        public Object getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(Object childBirthDate) {
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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(Object preChildDate) {
            this.preChildDate = preChildDate;
        }

        public int getReadTimes() {
            return readTimes;
        }

        public void setReadTimes(int readTimes) {
            this.readTimes = readTimes;
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
