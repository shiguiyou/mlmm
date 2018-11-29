package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;

/**
 * Created by XCF on 2018/9/30.
 */

public class GrowUpAppraisalResultBeans implements Serializable {
    /**
     * data : {"emotionConclusion":"感情与社会性能力：幼儿的社会性发展是其社会认知、社会情感和社会行为发展有机统一的过程。","emotionLink":"https://zhidao.baidu.com/question/1771695155167099340.html","generalConclusion":"YY在认知与语言，情感与社会性方面表现正常，在感知与运动方面有所欠缺，需要加强该方面的锻炼。","isNeedNewTest":false,"languageConclusion":"认知与语言能力：宝宝从出生起，大人就要多跟他讲话，形成一种语言环境，1岁以内都是宝宝的语言发育准备期。1岁以后，父母要有意识地培养宝宝说话，在做每个动作时都结合语言，更利于宝宝语言表达能力的提高","languageLink":"http://www.mama.cn/z/wiki/13976/","senseConclusion":"感知与运动能力：家长都期望孩子能长得高高壮壮的，于是有些父母非常注重饮食，有时还会给孩子吃钙片等营养品。其实运动也是帮助孩子成长的健康有效的方法。运动能锻炼孩子的身体，还能让他们保持舒畅的心情。","senseLink":"http://www.ci123.com/baike/760.html"}
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
         * emotionConclusion : 感情与社会性能力：幼儿的社会性发展是其社会认知、社会情感和社会行为发展有机统一的过程。
         * emotionLink : https://zhidao.baidu.com/question/1771695155167099340.html
         * generalConclusion : YY在认知与语言，情感与社会性方面表现正常，在感知与运动方面有所欠缺，需要加强该方面的锻炼。
         * isNeedNewTest : false
         * languageConclusion : 认知与语言能力：宝宝从出生起，大人就要多跟他讲话，形成一种语言环境，1岁以内都是宝宝的语言发育准备期。1岁以后，父母要有意识地培养宝宝说话，在做每个动作时都结合语言，更利于宝宝语言表达能力的提高
         * languageLink : http://www.mama.cn/z/wiki/13976/
         * senseConclusion : 感知与运动能力：家长都期望孩子能长得高高壮壮的，于是有些父母非常注重饮食，有时还会给孩子吃钙片等营养品。其实运动也是帮助孩子成长的健康有效的方法。运动能锻炼孩子的身体，还能让他们保持舒畅的心情。
         * senseLink : http://www.ci123.com/baike/760.html
         */

        private String emotionConclusion;
        private String emotionLink;
        private String generalConclusion;
        private boolean isNeedNewTest;
        private String languageConclusion;
        private String languageLink;
        private String senseConclusion;
        private String senseLink;

        public String getEmotionConclusion() {
            return emotionConclusion;
        }

        public void setEmotionConclusion(String emotionConclusion) {
            this.emotionConclusion = emotionConclusion;
        }

        public String getEmotionLink() {
            return emotionLink;
        }

        public void setEmotionLink(String emotionLink) {
            this.emotionLink = emotionLink;
        }

        public String getGeneralConclusion() {
            return generalConclusion;
        }

        public void setGeneralConclusion(String generalConclusion) {
            this.generalConclusion = generalConclusion;
        }

        public boolean isIsNeedNewTest() {
            return isNeedNewTest;
        }

        public void setIsNeedNewTest(boolean isNeedNewTest) {
            this.isNeedNewTest = isNeedNewTest;
        }

        public String getLanguageConclusion() {
            return languageConclusion;
        }

        public void setLanguageConclusion(String languageConclusion) {
            this.languageConclusion = languageConclusion;
        }

        public String getLanguageLink() {
            return languageLink;
        }

        public void setLanguageLink(String languageLink) {
            this.languageLink = languageLink;
        }

        public String getSenseConclusion() {
            return senseConclusion;
        }

        public void setSenseConclusion(String senseConclusion) {
            this.senseConclusion = senseConclusion;
        }

        public String getSenseLink() {
            return senseLink;
        }

        public void setSenseLink(String senseLink) {
            this.senseLink = senseLink;
        }
    }
}
