package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XCF on 2018/9/20.
 */

public class ParentingEncyclopediaTwoBeans implements Serializable {
    /**
     * data : [{"baikeList":[{"content":"","desc":"","icon":"产后护理","id":10,"title":"产后丰胸的最佳时间"},{"content":"","desc":"","icon":"产后护理","id":11,"title":"上环前注意事项"},{"content":"","desc":"","icon":"产后护理","id":12,"title":"到底坐月子能洗头吗"},{"content":"","desc":"","icon":"产后护理","id":14,"title":"产妇能吃螃蟹吗"},{"content":"","desc":"","icon":"产后护理","id":15,"title":"产妇采取什么睡姿"},{"content":"","desc":"","icon":"产后护理","id":16,"title":"哺乳期用药要谨慎"},{"content":"","desc":"","icon":"产后护理","id":17,"title":"产后胸部有什么变化"},{"content":"","desc":"","icon":"产后护理","id":18,"title":"上环是什么意思"},{"content":"","desc":"","icon":"产后护理","id":19,"title":"剖腹产后怎样护理伤口"},{"content":"","desc":"","icon":"产后护理","id":20,"title":"顺产后伤口怎么护理"},{"content":"","desc":"","icon":"产后护理","id":21,"title":"产后恶露是什么"},{"content":"","desc":"","icon":"产后护理","id":22,"title":"产后月经周期异常"},{"content":"","desc":"","icon":"产后护理","id":23,"title":"哺乳期感冒吃什么药"},{"content":"","desc":"","icon":"产后护理","id":24,"title":"产后丰胸秘籍"}],"icon":"产后护理"},{"baikeList":[{"content":"","desc":"","icon":"催乳","id":8,"title":"开奶"}],"icon":"催乳"},{"baikeList":[{"content":"","desc":"","icon":"新生儿喂养","id":13,"title":"奶水太多怎么办"}],"icon":"新生儿喂养"},{"baikeList":[{"content":"","desc":"","icon":"新生儿护理","id":9,"title":"新生儿脐带护理"}],"icon":"新生儿护理"}]
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
         * baikeList : [{"content":"","desc":"","icon":"产后护理","id":10,"title":"产后丰胸的最佳时间"},{"content":"","desc":"","icon":"产后护理","id":11,"title":"上环前注意事项"},{"content":"","desc":"","icon":"产后护理","id":12,"title":"到底坐月子能洗头吗"},{"content":"","desc":"","icon":"产后护理","id":14,"title":"产妇能吃螃蟹吗"},{"content":"","desc":"","icon":"产后护理","id":15,"title":"产妇采取什么睡姿"},{"content":"","desc":"","icon":"产后护理","id":16,"title":"哺乳期用药要谨慎"},{"content":"","desc":"","icon":"产后护理","id":17,"title":"产后胸部有什么变化"},{"content":"","desc":"","icon":"产后护理","id":18,"title":"上环是什么意思"},{"content":"","desc":"","icon":"产后护理","id":19,"title":"剖腹产后怎样护理伤口"},{"content":"","desc":"","icon":"产后护理","id":20,"title":"顺产后伤口怎么护理"},{"content":"","desc":"","icon":"产后护理","id":21,"title":"产后恶露是什么"},{"content":"","desc":"","icon":"产后护理","id":22,"title":"产后月经周期异常"},{"content":"","desc":"","icon":"产后护理","id":23,"title":"哺乳期感冒吃什么药"},{"content":"","desc":"","icon":"产后护理","id":24,"title":"产后丰胸秘籍"}]
         * icon : 产后护理
         */

        private String icon;
        private List<BaikeListBean> baikeList;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<BaikeListBean> getBaikeList() {
            return baikeList;
        }

        public void setBaikeList(List<BaikeListBean> baikeList) {
            this.baikeList = baikeList;
        }

        public static class BaikeListBean {
            /**
             * content :
             * desc :
             * icon : 产后护理
             * id : 10
             * title : 产后丰胸的最佳时间
             */

            private String content;
            private String desc;
            private String icon;
            private int id;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
