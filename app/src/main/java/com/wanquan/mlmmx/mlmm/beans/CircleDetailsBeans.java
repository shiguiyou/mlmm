package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class CircleDetailsBeans implements Serializable {

    /**
     * data : [{"childComment":[{"content":"123456","createTime":"2017-12-20 10:21:19","headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":96,"passiveHeadIco":"http://api.env365.cn/img/head/imgdefault@2x.png","passiveUserName":"17372205119","userName":"17372205119"},{"content":"fhdgfdgfdgfdg","createTime":"2017-12-20 10:23:49","headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":97,"passiveHeadIco":"http://api.env365.cn/img/head/imgdefault@2x.png","passiveUserName":"17372205119","userName":"17372205119"}],"content":"你注意安全啊","createTime":"2017-12-18 19:05:09","fid":null,"forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":66,"pid":0,"userId":103,"userName":"17372205119"},{"childComment":[],"content":"好好睡","createTime":"2017-12-18 19:05:47","fid":null,"forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":70,"pid":0,"userId":103,"userName":"17372205119"},{"childComment":[],"content":"卡","createTime":"2017-12-19 16:25:17","fid":null,"forumId":61,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":83,"pid":0,"userId":103,"userName":"17372205119"},{"childComment":[],"content":"Ffff","createTime":"2017-12-20 10:03:37","fid":null,"forumId":61,"headIco":"http://api.env365.cn/img/head/101_1711290829318711.png","id":92,"pid":0,"userId":101,"userName":"15261825221"}]
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
         * childComment : [{"content":"123456","createTime":"2017-12-20 10:21:19","headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":96,"passiveHeadIco":"http://api.env365.cn/img/head/imgdefault@2x.png","passiveUserName":"17372205119","userName":"17372205119"},{"content":"fhdgfdgfdgfdg","createTime":"2017-12-20 10:23:49","headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":97,"passiveHeadIco":"http://api.env365.cn/img/head/imgdefault@2x.png","passiveUserName":"17372205119","userName":"17372205119"}]
         * content : 你注意安全啊
         * createTime : 2017-12-18 19:05:09
         * fid : null
         * forumId : 61
         * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
         * id : 66
         * pid : 0
         * userId : 103
         * userName : 17372205119
         */

        private String content;
        private String createTime;
        private Object fid;
        private int forumId;
        private String headIco;
        private int id;
        private int pid;
        private int userId;
        private String userName;
        private String nickName;
        private List<ChildCommentBean> childComment;
        private boolean isShow = false;
        private String message;
        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
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

        public Object getFid() {
            return fid;
        }

        public void setFid(Object fid) {
            this.fid = fid;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
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

        public List<ChildCommentBean> getChildComment() {
            return childComment;
        }

        public void setChildComment(List<ChildCommentBean> childComment) {
            this.childComment = childComment;
        }

        public static class ChildCommentBean {
            /**
             * content : 123456
             * createTime : 2017-12-20 10:21:19
             * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
             * id : 96
             * passiveHeadIco : http://api.env365.cn/img/head/imgdefault@2x.png
             * passiveUserName : 17372205119
             * userName : 17372205119
             */

            private String content;
            private String createTime;
            private String headIco;
            private int id;
            private String passiveHeadIco;
            private String passiveUserName;
            private String passiveNickName;
            private String userName;
            private Object reviewUserId;
            private String nickName;
            private boolean isShow = false;


            public Object getReviewUserId() {
                return reviewUserId;
            }

            public void setReviewUserId(Object reviewUserId) {
                this.reviewUserId = reviewUserId;
            }

            public String getPassiveNickName() {
                return passiveNickName;
            }

            public void setPassiveNickName(String passiveNickName) {
                this.passiveNickName = passiveNickName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
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

            public String getPassiveHeadIco() {
                return passiveHeadIco;
            }

            public void setPassiveHeadIco(String passiveHeadIco) {
                this.passiveHeadIco = passiveHeadIco;
            }

            public String getPassiveUserName() {
                return passiveUserName;
            }

            public void setPassiveUserName(String passiveUserName) {
                this.passiveUserName = passiveUserName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
