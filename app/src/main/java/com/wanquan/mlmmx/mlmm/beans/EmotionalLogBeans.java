package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/19.
 */

public class EmotionalLogBeans implements Serializable {

    /**
     * data : [{"childBirthDate":"2017-01-01","content":null,"createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":242,"img":"http://api.env365.cn/voice/103_1712211005551952.png|http://api.env365.cn/voice/242_1712211005557936.png|http://api.env365.cn/voice/242_1712211005555518.png|http://api.env365.cn/voice/242_1712211005564091.png|http://api.env365.cn/voice/242_1712211005574450.png","preChildDate":"2017-12-30","status":1,"time":null,"userId":103,"voice":null},{"childBirthDate":"2017-01-01","content":"止","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":240,"img":"http://api.env365.cn/voice/103_1712210951227058.png","preChildDate":"2017-12-30","status":1,"time":null,"userId":103,"voice":null},{"childBirthDate":"2017-01-01","content":"痒","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":239,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210949473004.amr"},{"childBirthDate":"2017-01-01","content":"王","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":238,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210948124818.amr"},{"childBirthDate":"2017-01-01","content":"一些事情","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":237,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210946438677.amr"},{"childBirthDate":"2017-01-01","content":"聊聊天","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":236,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210945150265.amr"},{"childBirthDate":"2017-01-01","content":"bhh蛋蛋","createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":235,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210939203679.amr"},{"childBirthDate":"2017-01-01","content":null,"createTime":"2017-12-21","dateReminder":"宝宝11个月20天","id":234,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712210936502353.amr"},{"childBirthDate":"2017-01-01","content":null,"createTime":"2017-12-08","dateReminder":"宝宝11个月7天","id":185,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712081037304730.amr"},{"childBirthDate":"2017-01-01","content":null,"createTime":"2017-12-08","dateReminder":"宝宝11个月7天","id":184,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712081027327569.amr"},{"childBirthDate":"2017-01-01","content":null,"createTime":"2017-12-08","dateReminder":"宝宝11个月7天","id":178,"img":null,"preChildDate":"2017-12-30","status":1,"time":10,"userId":103,"voice":"http://api.env365.cn/voice/103_1712080855517429.amr"}]
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
         * childBirthDate : 2017-01-01
         * content : null
         * createTime : 2017-12-21
         * dateReminder : 宝宝11个月20天
         * id : 242
         * img : http://api.env365.cn/voice/103_1712211005551952.png|http://api.env365.cn/voice/242_1712211005557936.png|http://api.env365.cn/voice/242_1712211005555518.png|http://api.env365.cn/voice/242_1712211005564091.png|http://api.env365.cn/voice/242_1712211005574450.png
         * preChildDate : 2017-12-30
         * status : 1
         * time : null
         * userId : 103
         * voice : null
         */

        private String childBirthDate;
        private String content;
        private String createTime;
        private String dateReminder;
        private int id;
        private String img;
        private String preChildDate;
        private int status;
        private Object time;
        private int userId;
        private String voice;

        public String getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(String childBirthDate) {
            this.childBirthDate = childBirthDate;
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

        public String getDateReminder() {
            return dateReminder;
        }

        public void setDateReminder(String dateReminder) {
            this.dateReminder = dateReminder;
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

        public String getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(String preChildDate) {
            this.preChildDate = preChildDate;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }
    }
}
