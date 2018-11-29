package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XCF on 2018/9/28.
 */

public class ParentingEncyclopediaSearchBeans implements Serializable {
    /**
     * data : [{"content":"","desc":"什么是孕前准备呢?孕前准备就是孕前三个月在生活、饮食、心理、身体检查等诸多方面做","icon":"孕前准备","id":9,"img":"http://api.env365.cn/img/baike/beiyun.png","title":"怀孕前准备"},{"content":"","desc":"什么是孕前准备呢?孕前准备就是孕前三个月在生活、饮食、心理、身体检查等诸多方面做","icon":"备孕常识","id":27,"img":"http://api.env365.cn/img/baike/beiyun.png","title":"怀孕前的准备"},{"content":"","desc":"1、调整饮食：多吃富有营养的食物，基本不吃油炸食物和寒性食物，比如白萝卜。同样水","icon":"备孕常识","id":44,"img":"http://api.env365.cn/img/baike/beiyun.png","title":"怀孕前准备"},{"content":"","desc":"上环后会怀孕吗?据不完全统计，上了环也是可以怀孕的，怀孕的概率大概是3%-4%。","icon":"备孕常识","id":54,"img":"http://api.env365.cn/img/baike/beiyun.png","title":"上环后会怀孕"},{"content":"","desc":"现如今，很多地区怀二胎已经开始实行。对于年龄过大的高龄孕妇在怀二胎的孕期里要特别","icon":"备孕常识","id":87,"img":"http://api.env365.cn/img/baike/beiyun.png","title":"高龄妈妈怀孕"},{"content":"","desc":"受孕几天能测出来?一般来说，在同房后的7\u201410天即可自行进行检测，可以通过早孕试","icon":"孕期检查","id":91,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕测试"},{"content":"","desc":"一般40天可以看到胎芽胎心。孕囊达到1cm左右，应该可以看到胎心了。在怀孕第四十","icon":"孕期检查","id":99,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕40天"},{"content":"","desc":"事实证明，有心理准备的孕妇与没有心理准备孕妇相比，前者的孕期生活要顺利从容得多，","icon":"孕期护理","id":114,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕心理准备"},{"content":"","desc":"一般情况下，人的正常体温是36～37℃(腋下测量)，而怀孕初期孕妇的体温会升高，","icon":"孕期检查","id":118,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕初期体温"},{"content":"","desc":"感冒对于我们来说是一种常见的疾病之一，但是在怀孕期间感冒就很不好了，所以各位孕妈","icon":"孕期疾病","id":125,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕初期感冒"},{"content":"","desc":"女性在怀孕六周左右会出现怀孕症状，如月经推迟、基础体温持续上升、恶心呕吐、尿频、","icon":"孕期常识","id":165,"img":"http://api.env365.cn/img/baike/huaiyun.png","title":"怀孕多久有反应"}]
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
         * content :
         * desc : 什么是孕前准备呢?孕前准备就是孕前三个月在生活、饮食、心理、身体检查等诸多方面做
         * icon : 孕前准备
         * id : 9
         * img : http://api.env365.cn/img/baike/beiyun.png
         * title : 怀孕前准备
         */

        private String content;
        private String desc;
        private String icon;
        private int id;
        private String img;
        private String title;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
