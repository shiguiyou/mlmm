package com.wanquan.mlmmx.mlmm.xmly.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class ParentTaskBeans implements Serializable {
    /**
     * data : {"Vo":[{"list":[{"WEEK":0,"done":"1","id":1330,"title":"宝宝出生了，妈妈可以开奶了 ","url":"http://stlib.qbb6.com/cnt0/article/0/8728MP1TAD/1504001313447.html"},{"WEEK":0,"done":"0","id":1331,"title":"了解新生宝宝众多的\u201c第一次\u201d ","url":"http://stlib.qbb6.com/cnt0/article/0/8905oeBt7b/1514968210280.html"},{"WEEK":0,"done":"0","id":1332,"title":"了解新生儿最初几天的外观和行为变化 ","url":"http://stlib.qbb6.com/cnt0/article/0/8909w4zas5/1514970066663.html"}],"week":"0"},{"list":[{"WEEK":1,"done":"0","id":1333,"title":"在呵护宝宝时，千万别忽略了对妈妈的关心 ","url":"http://stlib.qbb6.com/cnt0/article/0/8907GskCm8/1514970656811.html"},{"WEEK":1,"done":"0","id":1334,"title":"宝宝出生后要办的证，你都办了没 ","url":"http://stlib.qbb6.com/cnt0/article/0/89084L6fti/1514970513746.html"},{"WEEK":1,"done":"0","id":1335,"title":"检查宝宝脐带是否正常，注意保持干燥 ","url":"http://stlib.qbb6.com/cnt0/article/0/8483iBgUu1/1514970872218.html"}],"week":"1"},{"list":[{"WEEK":2,"done":"0","id":1336,"title":"给宝宝洗澡，让宝宝舒舒服服地睡觉 ","url":"http://stlib.qbb6.com/cnt0/article/0/8688xdhqZ8/1508472990314.html"},{"WEEK":2,"done":"0","id":1337,"title":"爸爸要学习如何麻利地给宝宝换尿布哦 ","url":"http://stlib.qbb6.com/cnt0/article/0/4981dGGjTf/1511177477072.html"},{"WEEK":2,"done":"0","id":1338,"title":"检查宝宝的小床，让玩具小零件远离宝宝 ","url":"http://stlib.qbb6.com/cnt0/article/0/7128LrF72W/1473749808645.html"}],"week":"2"}],"size":3}
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
         * Vo : [{"list":[{"WEEK":0,"done":"1","id":1330,"title":"宝宝出生了，妈妈可以开奶了 ","url":"http://stlib.qbb6.com/cnt0/article/0/8728MP1TAD/1504001313447.html"},{"WEEK":0,"done":"0","id":1331,"title":"了解新生宝宝众多的\u201c第一次\u201d ","url":"http://stlib.qbb6.com/cnt0/article/0/8905oeBt7b/1514968210280.html"},{"WEEK":0,"done":"0","id":1332,"title":"了解新生儿最初几天的外观和行为变化 ","url":"http://stlib.qbb6.com/cnt0/article/0/8909w4zas5/1514970066663.html"}],"week":"0"},{"list":[{"WEEK":1,"done":"0","id":1333,"title":"在呵护宝宝时，千万别忽略了对妈妈的关心 ","url":"http://stlib.qbb6.com/cnt0/article/0/8907GskCm8/1514970656811.html"},{"WEEK":1,"done":"0","id":1334,"title":"宝宝出生后要办的证，你都办了没 ","url":"http://stlib.qbb6.com/cnt0/article/0/89084L6fti/1514970513746.html"},{"WEEK":1,"done":"0","id":1335,"title":"检查宝宝脐带是否正常，注意保持干燥 ","url":"http://stlib.qbb6.com/cnt0/article/0/8483iBgUu1/1514970872218.html"}],"week":"1"},{"list":[{"WEEK":2,"done":"0","id":1336,"title":"给宝宝洗澡，让宝宝舒舒服服地睡觉 ","url":"http://stlib.qbb6.com/cnt0/article/0/8688xdhqZ8/1508472990314.html"},{"WEEK":2,"done":"0","id":1337,"title":"爸爸要学习如何麻利地给宝宝换尿布哦 ","url":"http://stlib.qbb6.com/cnt0/article/0/4981dGGjTf/1511177477072.html"},{"WEEK":2,"done":"0","id":1338,"title":"检查宝宝的小床，让玩具小零件远离宝宝 ","url":"http://stlib.qbb6.com/cnt0/article/0/7128LrF72W/1473749808645.html"}],"week":"2"}]
         * size : 3
         */

        private int size;
        private List<VoBean> Vo;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<VoBean> getVo() {
            return Vo;
        }

        public void setVo(List<VoBean> Vo) {
            this.Vo = Vo;
        }

        public static class VoBean {
            /**
             * list : [{"WEEK":0,"done":"1","id":1330,"title":"宝宝出生了，妈妈可以开奶了 ","url":"http://stlib.qbb6.com/cnt0/article/0/8728MP1TAD/1504001313447.html"},{"WEEK":0,"done":"0","id":1331,"title":"了解新生宝宝众多的\u201c第一次\u201d ","url":"http://stlib.qbb6.com/cnt0/article/0/8905oeBt7b/1514968210280.html"},{"WEEK":0,"done":"0","id":1332,"title":"了解新生儿最初几天的外观和行为变化 ","url":"http://stlib.qbb6.com/cnt0/article/0/8909w4zas5/1514970066663.html"}]
             * week : 0
             */

            private String week;
            private List<ListBean> list;

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * WEEK : 0
                 * done : 1
                 * id : 1330
                 * title : 宝宝出生了，妈妈可以开奶了
                 * url : http://stlib.qbb6.com/cnt0/article/0/8728MP1TAD/1504001313447.html
                 */

                private int WEEK;
                private String done;
                private int id;
                private String title;
                private String url;

                public int getWEEK() {
                    return WEEK;
                }

                public void setWEEK(int WEEK) {
                    this.WEEK = WEEK;
                }

                public String getDone() {
                    return done;
                }

                public void setDone(String done) {
                    this.done = done;
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

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
