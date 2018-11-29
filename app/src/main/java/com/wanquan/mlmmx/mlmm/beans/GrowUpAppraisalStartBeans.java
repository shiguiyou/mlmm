package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XCF on 2018/9/29.
 */

public class GrowUpAppraisalStartBeans implements Serializable {
    /**
     * data : [{"question":"能用脚尖走路（4-5 步）但走的不稳，能一手扶栏杆上下 4-8 阶楼梯？"},{"question":"宝宝能够跑得很稳，较少摔跤？"},{"question":"宝宝能双手举过头顶掷球，会向不同方向抛球，能踢球？"},{"question":"宝宝能根据音乐的节奏做动作？"},{"question":"宝宝能说出自己和父母的名字？"},{"question":"宝宝能说 3-5 个字的句子，能从1数到5？"},{"question":"喜欢听重复的声音，如反复听一首歌？"},{"question":"宝宝能回答生活中的简单问题？"},{"question":"当父母或看护人离开房间时会感到沮丧，与父母分离时会产生恐惧？"},{"question":"在有提示的情况下，会说\u201c请\u201d和\u201c谢谢\u201d？"},{"question":"宝宝能对自己独立表现的一些技能感到骄傲？"},{"question":"宝宝不愿把东西给别人，喜欢说\u201c这是我的、不\u201d？"}]
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
         * question : 能用脚尖走路（4-5 步）但走的不稳，能一手扶栏杆上下 4-8 阶楼梯？
         */

        private String question;

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }
    }
}
