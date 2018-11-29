package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */

public class CircleDetailNewBeans implements Serializable {
    /**
     * data : {"desc":"想聊就聊，开心就好","fornumList":[{"childBirthDate":null,"commentCount":3,"content":"大多数事情，不是你想明白后才觉得无所谓，而是你无所谓之后才突然想明白的。","createTime":"2018-01-09","follow":null,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":165,"img":null,"nickName":null,"preChildDate":1525104000000,"readTimes":797,"title":"且敬往事一杯酒，愿无岁月可回头。","type":"13","updateTime":"2018-01-09","userId":145,"userName":"15850565615"},{"childBirthDate":null,"commentCount":null,"content":"领导问我，给你发短信为什么不回？我说没看到不好意思马上看。短信一看是通知我看微信记录，点开微信发现一条QQ留言：\u201c给你发了个邮件，在邮箱里\u201d我顿时感受到了高科技生活给我们带来的便利。","createTime":"2018-01-09","follow":null,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":167,"img":"forum/145_1801090928301020.png","nickName":null,"preChildDate":1525104000000,"readTimes":786,"title":"哈哈哈哈","type":"13","updateTime":"2018-01-09","userId":145,"userName":"15850565615"}],"id":13,"img":"http://192.168.1.207:12001/img/circle/chat.png","isFollow":null,"name":"谈天说地","parentId":1,"pic":"http://192.168.1.207:12001/img/circle/chat.png"}
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
         * desc : 想聊就聊，开心就好
         * fornumList : [{"childBirthDate":null,"commentCount":3,"content":"大多数事情，不是你想明白后才觉得无所谓，而是你无所谓之后才突然想明白的。","createTime":"2018-01-09","follow":null,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":165,"img":null,"nickName":null,"preChildDate":1525104000000,"readTimes":797,"title":"且敬往事一杯酒，愿无岁月可回头。","type":"13","updateTime":"2018-01-09","userId":145,"userName":"15850565615"},{"childBirthDate":null,"commentCount":null,"content":"领导问我，给你发短信为什么不回？我说没看到不好意思马上看。短信一看是通知我看微信记录，点开微信发现一条QQ留言：\u201c给你发了个邮件，在邮箱里\u201d我顿时感受到了高科技生活给我们带来的便利。","createTime":"2018-01-09","follow":null,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","id":167,"img":"forum/145_1801090928301020.png","nickName":null,"preChildDate":1525104000000,"readTimes":786,"title":"哈哈哈哈","type":"13","updateTime":"2018-01-09","userId":145,"userName":"15850565615"}]
         * id : 13
         * img : http://192.168.1.207:12001/img/circle/chat.png
         * isFollow : null
         * name : 谈天说地
         * parentId : 1
         * pic : http://192.168.1.207:12001/img/circle/chat.png
         */

        private String desc;
        private int id;
        private String img;
        private String isFollow;
        private String name;
        private int parentId;
        private String pic;
        private List<FornumListBean> fornumList;
        private int memberNum;
        private int forumCount;


        public int getForumCount() {
            return forumCount;
        }

        public void setForumCount(int forumCount) {
            this.forumCount = forumCount;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
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

        public String getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(String isFollow) {
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

        public List<FornumListBean> getFornumList() {
            return fornumList;
        }

        public void setFornumList(List<FornumListBean> fornumList) {
            this.fornumList = fornumList;
        }

        public static class FornumListBean {
            /**
             * childBirthDate : null
             * commentCount : 3
             * content : 大多数事情，不是你想明白后才觉得无所谓，而是你无所谓之后才突然想明白的。
             * createTime : 2018-01-09
             * follow : null
             * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
             * id : 165
             * img : null
             * nickName : null
             * preChildDate : 1525104000000
             * readTimes : 797
             * title : 且敬往事一杯酒，愿无岁月可回头。
             * type : 13
             * updateTime : 2018-01-09
             * userId : 145
             * userName : 15850565615
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
            private long preChildDate;
            private int readTimes;
            private String title;
            private String type;
            private String updateTime;
            private int userId;
            private String userName;
            private boolean collect;
            private String circleName;
            private String circleId;
            private String message;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getCircleName() {
                return circleName;
            }

            public void setCircleName(String circleName) {
                this.circleName = circleName;
            }

            public String getCircleId() {
                return circleId;
            }

            public void setCircleId(String circleId) {
                this.circleId = circleId;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

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
}
