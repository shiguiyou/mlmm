package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XCF on 2018/11/2.
 */

public class BabyRespiratoryReportCardPHBBeans implements Serializable {
    /**
     * data : [{"childBirthDate":null,"create_time":1540485000000,"deviceId":63,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":1,"userId":500,"username":"美丽妈妈用户558665","value":1},{"childBirthDate":1394321964000,"create_time":1540485000000,"deviceId":115,"headIco":"http://api.env365.cn/img/head/62_1803120924102229.png","message":"宝宝4年7个月24天","preChildDate":null,"rownum":2,"userId":62,"username":"核弹头","value":11},{"childBirthDate":1483200000000,"create_time":1540485000000,"deviceId":24,"headIco":"http://api.env365.cn/img/head/61_1712060401139498.png","message":"宝宝1年10个月1天","preChildDate":null,"rownum":3,"userId":61,"username":"等等","value":13},{"childBirthDate":null,"create_time":1540618200000,"deviceId":31,"headIco":"http://api3.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":4,"userId":535,"username":"美丽妈妈用户987461","value":14},{"childBirthDate":null,"create_time":1540485000000,"deviceId":77,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":5,"userId":36,"username":"美丽妈妈用户817630","value":14},{"childBirthDate":null,"create_time":1540485000000,"deviceId":101,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":6,"userId":55,"username":"美丽妈妈用户918707","value":16},{"childBirthDate":null,"create_time":1540513800000,"deviceId":78,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":7,"userId":52,"username":"美丽妈妈用户572718","value":17},{"childBirthDate":null,"create_time":1540485000000,"deviceId":80,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":9,"userId":25,"username":"美丽妈妈用户845466","value":28},{"childBirthDate":null,"create_time":1540485000000,"deviceId":66,"headIco":"http://api.env365.cn/img/head/imgdefault@2x.png","message":"备孕中","preChildDate":null,"rownum":10,"userId":400,"username":"美丽妈妈用户933609","value":29},{"childBirthDate":null,"create_time":1540485000000,"deviceId":64,"headIco":"http://api.env365.cn/img/head/26_1710200201186091.png","message":"备孕中","preChildDate":null,"rownum":11,"userId":26,"username":"tyg","value":33}]
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
         * childBirthDate : null
         * create_time : 1540485000000
         * deviceId : 63
         * headIco : http://api.env365.cn/img/head/imgdefault@2x.png
         * message : 备孕中
         * preChildDate : null
         * rownum : 1
         * userId : 500
         * username : 美丽妈妈用户558665
         * value : 1
         */

        private Object childBirthDate;
        private long create_time;
        private int deviceId;
        private String headIco;
        private String message;
        private Object preChildDate;
        private int rownum;
        private String userId;
        private String username;
        private int value;

        public Object getChildBirthDate() {
            return childBirthDate;
        }

        public void setChildBirthDate(Object childBirthDate) {
            this.childBirthDate = childBirthDate;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getHeadIco() {
            return headIco;
        }

        public void setHeadIco(String headIco) {
            this.headIco = headIco;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getPreChildDate() {
            return preChildDate;
        }

        public void setPreChildDate(Object preChildDate) {
            this.preChildDate = preChildDate;
        }

        public int getRownum() {
            return rownum;
        }

        public void setRownum(int rownum) {
            this.rownum = rownum;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
