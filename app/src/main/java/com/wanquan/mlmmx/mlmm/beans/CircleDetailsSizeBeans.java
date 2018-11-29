package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by XCF on 2018/8/30.
 */

public class CircleDetailsSizeBeans implements Serializable {
    /**
     * data : {"childBirthDate":null,"circleId":10,"circleName":"婆媳关系","commentCount":8,"content":"刚刚","createTime":"2018-08-29","follow":532,"headIco":"http://192.168.1.207/img/head/imgdefault@2x.png","id":532,"img":"http://192.168.1.207/forum/620_1808290525248931.png|","isFollowOne":false,"message":"孕40周2天","nickName":"小天才","preChildDate":1535472000000,"readTimes":7,"title":"婆媳关系","type":"10","uid":null,"updateTime":"2018-08-29","userId":620,"userName":"13913016920"}
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
         * circleId : 10
         * circleName : 婆媳关系
         * commentCount : 8
         * content : 刚刚
         * createTime : 2018-08-29
         * follow : 532
         * headIco : http://192.168.1.207/img/head/imgdefault@2x.png
         * id : 532
         * img : http://192.168.1.207/forum/620_1808290525248931.png|
         * isFollowOne : false
         * message : 孕40周2天
         * nickName : 小天才
         * preChildDate : 1535472000000
         * readTimes : 7
         * title : 婆媳关系
         * type : 10
         * uid : null
         * updateTime : 2018-08-29
         * userId : 620
         * userName : 13913016920
         */

        private Object childBirthDate;
        private int circleId;
        private String circleName;
        private int commentCount;
        private String content;
        private String createTime;
        private int follow;
        private String headIco;
        private int id;
        private String img;
        private boolean isFollowOne;
        private String message;
        private String nickName;
        private long preChildDate;
        private int readTimes;
        private String title;
        private String type;
        private Object uid;
        private String updateTime;
        private int userId;
        private String userName;

        public Object getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(Object childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public int getCircleId() {
            return circleId;
        }

        public void setCircleId(int circleId) {
            this.circleId = circleId;
        }

        public String getCircleName() {
            return circleName;
        }

        public void setCircleName(String circleName) {
            this.circleName = circleName;
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

        public boolean isIsFollowOne() {
            return isFollowOne;
        }

        public void setIsFollowOne(boolean isFollowOne) {
            this.isFollowOne = isFollowOne;
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

        public Object getUid() {
            return uid;
        }

        public void setUid(Object uid) {
            this.uid = uid;
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
