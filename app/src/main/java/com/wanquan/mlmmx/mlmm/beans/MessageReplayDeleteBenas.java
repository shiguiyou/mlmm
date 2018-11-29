package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class MessageReplayDeleteBenas implements Serializable {
    /**
     * data : [{"commentId":66,"content":"你注意安全啊","createTime":"2017-12-18 19:05:09","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43448,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":67,"content":"王","createTime":"2017-12-18 19:05:16","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43449,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":68,"content":"姐姐妹妹站起来","createTime":"2017-12-18 19:05:22","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43450,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":69,"content":"马上就能骊","createTime":"2017-12-18 19:05:34","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43451,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":70,"content":"好好睡","createTime":"2017-12-18 19:05:47","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43452,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":73,"content":"123456","createTime":"2017-12-18 19:23:22","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43455,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":74,"content":"123456","createTime":"2017-12-18 19:24:33","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43456,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":75,"content":"123456","createTime":"2017-12-18 19:24:54","forumId":61,"headIco":"http://api.env365.cn/img/head/1_1711211134078817.png","notificationId":43457,"readStatus":0,"title":"敢了","userId":1,"userName":"13800000000"},{"commentId":76,"content":"女孩子","createTime":"2017-12-18 19:26:33","forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","notificationId":43458,"readStatus":0,"title":"敢了","userId":103,"userName":"17372205119"},{"commentId":77,"content":"sghs","createTime":"2017-12-18 19:56:38","forumId":61,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","notificationId":43459,"readStatus":0,"title":"敢了","userId":101,"userName":"15261825221"},{"commentId":78,"content":"符合哈哈","createTime":"2017-12-18 19:58:15","forumId":61,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","notificationId":43460,"readStatus":0,"title":"敢了","userId":101,"userName":"15261825221"}]
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
         * commentId : 66
         * content : 你注意安全啊
         * createTime : 2017-12-18 19:05:09
         * forumId : 61
         * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
         * notificationId : 43448
         * readStatus : 0
         * title : 敢了
         * userId : 103
         * userName : 17372205119
         */

        private int commentId;
        private String content;
        private String createTime;
        private int forumId;
        private String headIco;
        private int notificationId;
        private int readStatus;
        private String title;
        private int userId;
        private String userName;

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
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

        public int getForumId() {
            return forumId;
        }

        public void setForumId(int forumId) {
            this.forumId = forumId;
        }

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
        }

        public int getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(int notificationId) {
            this.notificationId = notificationId;
        }

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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
